package com.phorest.techtest.wojciechowski.clientservice.service.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Customer {
    private String id;
    private List<Appointment> appointments = new ArrayList<>();
}
