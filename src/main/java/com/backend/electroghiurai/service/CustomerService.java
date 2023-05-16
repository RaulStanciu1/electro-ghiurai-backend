package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.Customer;
import com.backend.electroghiurai.exception.IncorrectPasswordException;
import com.backend.electroghiurai.exception.UsernameExistsException;
import com.backend.electroghiurai.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;
    public Customer registerCustomer(Customer customer){
        if(usernameAlreadyExists(customer.getUsername())){
            throw new UsernameExistsException("Username is already in use.");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(customer.getPassword());
        customer.setPassword(encryptedPassword);
        return repository.save(customer);
    }
    public Customer getCustomer(String username, String password){
        Customer customer = repository.findByUsername(username);
        if(customer == null){
            throw new UsernameNotFoundException("Username doesn't exist.");
        }
        BCryptPasswordEncoder matcher = new BCryptPasswordEncoder();
        if(!matcher.matches(password, customer.getPassword())){
            throw new IncorrectPasswordException("Username and password don't match");
        }
        return customer;
    }
    private boolean usernameAlreadyExists(String username){
        return repository.findByUsername(username) != null;
    }

    public Customer getCustomerById(Long id){
        return repository.findByCustomerId(id);
    }
}
