package com.phorest.techtest.wojciechowski.clientservice.service.mapping.csv;

import com.phorest.techtest.wojciechowski.clientservice.service.model.Appointment;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Customer;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class CSVMappingHelper {
    public static Map<String, BiConsumer<Customer, Object>>  csvClientMapping() {
        Map<String, BiConsumer<Customer, Object>> mapping = new HashMap<>();
        mapping.put("id", (customer, idStirng) -> customer.setId((String)idStirng));
        return mapping;
    }

    public static Map<String, BiConsumer<Appointment, Object>> csvClientAppointment() {
        Map<String, BiConsumer<Appointment, Object>> mapping = new HashMap<>();
        mapping.put("client_id", (appointment, idStirng) -> appointment.setClientId((String)idStirng));
        mapping.put("id", (appointment, idStirng) -> appointment.setId((String)idStirng));
        return mapping;
    }

    public static Map<String, BiConsumer<Service, Object>>  csvClientService() {
        Map<String, BiConsumer<Service, Object>> mapping = new HashMap<>();
        mapping.put("appointment_id", (service, idStirng) -> service.setAppointmentId((String)idStirng));
        mapping.put("id", (appointmentPair, idStirng) -> appointmentPair.setId((String)idStirng));
        return mapping;
    }

}
