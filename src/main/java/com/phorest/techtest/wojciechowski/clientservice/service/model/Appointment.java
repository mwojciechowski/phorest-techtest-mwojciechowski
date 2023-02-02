package com.phorest.techtest.wojciechowski.clientservice.service.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Appointment {
    String clientId;
    String id;
    List<Service> services = new ArrayList<>();
}
