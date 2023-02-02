package com.phorest.techtest.wojciechowski.clientservice.service;

import com.phorest.techtest.wojciechowski.clientservice.dao.EntityRepository;
import com.phorest.techtest.wojciechowski.clientservice.service.exception.ValidationError;
import com.phorest.techtest.wojciechowski.clientservice.service.mapping.csv.CsvMapper;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Appointment;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Customer;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Service;
import com.phorest.techtest.wojciechowski.clientservice.service.parsing.EntityCSVParser;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UTImportingServiceTest {

    @Test
    public void serviceShouldImportCustomerWithAppointmentAndService() throws ValidationError {
        String clients = "id, name\r\n" +
                "1, Name1";
        String appointments = "client_id, id\r\n" +
                "1, 2";
        String services = "appointment_id, id\r\n" +
                "2, 3";
        InputStream customersStream = new ByteArrayInputStream(clients.getBytes());
        InputStream appointmentsStream = new ByteArrayInputStream(appointments.getBytes());
        InputStream servicesStream = new ByteArrayInputStream(services.getBytes());

        CsvMapper<Customer> customerCsvMapper = Mockito.mock(CsvMapper.class);
        Customer customer = new Customer();
        customer.setId("1");
        when(customerCsvMapper.map(any())).thenReturn(customer);

        CsvMapper<Appointment> appointmentCsvMapper = Mockito.mock(CsvMapper.class);
        Appointment appointment = new Appointment();
        appointment.setClientId("1");
        appointment.setId("2");
        when(appointmentCsvMapper.map(any())).thenReturn(appointment);

        CsvMapper<Service> serviceCsvMapper = Mockito.mock(CsvMapper.class);
        Service service = new Service();
        service.setAppointmentId("2");
        service.setId("3");
        when(serviceCsvMapper.map(any())).thenReturn(service);

        EntityRepository<Customer, String> customerEntityRepository = Mockito.mock(EntityRepository.class);

        ImportingService importingService = new ImportingService(new EntityCSVParser(","), customerCsvMapper,
                appointmentCsvMapper, serviceCsvMapper, customerEntityRepository);

        importingService.importFromFiles(customersStream, appointmentsStream, servicesStream, null);


        verify(customerEntityRepository).save(argThat((ArgumentMatcher<Collection<Customer>>) list -> {
            if(list == null || list.size() != 1) {
                return false;
            }
            Customer c = list.iterator().next();
            if(!"1".equals(c.getId()) || c.getAppointments() == null || c.getAppointments().size() != 1) {
                return false;
            }
            Appointment a = c.getAppointments().get(0);
            if(!"2".equals(a.getId()) || a.getServices() == null || a.getServices().size() != 1) {
                return false;
            }
            Service s = a.getServices().get(0);
            if(!"3".equals(s.getId())) {
                return false;
            }
            return true;
        }));

    }
}
