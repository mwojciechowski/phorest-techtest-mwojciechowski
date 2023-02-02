package com.phorest.techtest.wojciechowski.clientservice.web;

import com.phorest.techtest.wojciechowski.clientservice.service.EntityService;
import com.phorest.techtest.wojciechowski.clientservice.service.ImportingService;
import com.phorest.techtest.wojciechowski.clientservice.service.exception.ValidationError;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("/import")
@RequiredArgsConstructor
public class Controller {

    private final ImportingService importingService;

    @PostMapping("/customers")
    public HttpEntity<String> importCustomers  (
            @RequestParam("customers") MultipartFile customers,
            @RequestParam("appointments") MultipartFile appointments,
            @RequestParam("services") MultipartFile services,
            @RequestParam("services") MultipartFile purchases
    ) throws ValidationError {
        try {
            importingService.importFromFiles(
                    customers.getInputStream(),
                    appointments.getInputStream(),
                    services.getInputStream(),
                    purchases.getInputStream());
        } catch (IOException exception) {
            throw new ValidationError("Cannot read input data", exception);
        }
        return new HttpEntity("OK");
    }
}
