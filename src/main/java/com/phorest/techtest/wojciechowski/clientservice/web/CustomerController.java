package com.phorest.techtest.wojciechowski.clientservice.web;

import com.phorest.techtest.wojciechowski.clientservice.service.EntityService;
import com.phorest.techtest.wojciechowski.clientservice.service.exception.ValidationError;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final EntityService<Customer, String> customerService;

    @PutMapping("/create")
    public HttpEntity<Customer> createCustomer  (
            @RequestBody Customer customer
    ) throws ValidationError {
        customer = customerService.createEntity(customer);
        return new HttpEntity<>(customer);
    }
}
