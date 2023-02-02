package com.phorest.techtest.wojciechowski.clientservice.service;

import com.phorest.techtest.wojciechowski.clientservice.dao.EntityRepository;
import com.phorest.techtest.wojciechowski.clientservice.service.exception.MappingError;
import com.phorest.techtest.wojciechowski.clientservice.service.exception.ValidationError;
import com.phorest.techtest.wojciechowski.clientservice.service.mapping.csv.CsvMapper;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Appointment;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Customer;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Service;
import com.phorest.techtest.wojciechowski.clientservice.service.parsing.EntityCSVParser;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ImportingService {
    EntityCSVParser parser;

    CsvMapper<Customer> clientCsvMapper;

    CsvMapper<Appointment> appointmentCsvMapper;

    CsvMapper<Service> serviceCsvMapper;

    EntityRepository<Customer, String> customerEntityRepository;

    public void importFromFiles(
            InputStream clientsStream,
            InputStream appointmentsStream,
            InputStream servicesStream,
            InputStream purchasesStream) throws ValidationError {
        Map<String, Customer> customers = getEntitiesMap(clientsStream, clientCsvMapper, Customer::getId, "client" );

        Map<String, Appointment> appointments =
                getEntitiesMap(appointmentsStream, appointmentCsvMapper,
                        appointment -> appointment.getId(), "appointment");

        appointments.values().forEach(appointment ->
                Optional.ofNullable(customers.get(appointment.getClientId()))
                        .ifPresent(customer -> customer.getAppointments().add(appointment))
        );

        Map<String, Service> services =
                getEntitiesMap(servicesStream, serviceCsvMapper,
                        service -> service.getId(), "service");

        services.values().forEach(service ->
                Optional.ofNullable(appointments.get(service.getAppointmentId()))
                        .ifPresent(appointment -> appointment.getServices().add(service))
        );

        customerEntityRepository.save(customers.values());
    }

    private <T> Map<String, T> getEntitiesMap(InputStream stream, CsvMapper<T> mapper, Function<T, String> idExtractor,
                                              String entityMessage)
            throws ValidationError {
        try {
            List<CSVRecord> clientsRecords = parser.parse(new InputStreamReader(stream));
            Map<String, T> entities = clientsRecords.stream().map(entityCsv -> mapper.map(entityCsv))
                    .collect(Collectors.toMap(idExtractor, entity -> entity));
            return entities;
        } catch(IOException exc) {
            throw new ValidationError("Invalid format of " + entityMessage + " data", exc);
        } catch(MappingError exc) {
            throw new ValidationError("Invalid value of " + entityMessage + " data", exc);
        }
    }
}
