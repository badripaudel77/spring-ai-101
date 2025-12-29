package com.ai.myspring.tools;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Customer> getCustomer(@RequestParam(value = "prompt", defaultValue = "Find User with Id 1") String prompt) {
        return ResponseEntity.ok(service.getCustomerById(prompt));
    }
}
