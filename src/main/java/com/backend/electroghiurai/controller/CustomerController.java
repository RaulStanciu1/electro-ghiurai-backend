package com.backend.electroghiurai.controller;

import com.backend.electroghiurai.entity.Customer;
import com.backend.electroghiurai.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService service;
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomerToDB(@RequestBody Customer customer){
        Customer record = service.registerCustomer(customer);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> loginCustomer(@RequestBody Map<String,String> userData){
        String username = userData.get("username");
        String password = userData.get("password");
        Customer loggedInCustomer = service.getCustomer(username,password);
        return new ResponseEntity<>(loggedInCustomer,HttpStatus.OK);
    }
}
