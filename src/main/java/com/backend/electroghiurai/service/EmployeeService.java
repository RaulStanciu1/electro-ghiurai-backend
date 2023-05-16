package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.Employee;
import com.backend.electroghiurai.entity.EmployeeForm;
import com.backend.electroghiurai.entity.InternalOrder;
import com.backend.electroghiurai.exception.IncorrectPasswordException;
import com.backend.electroghiurai.exception.UsernameExistsException;
import com.backend.electroghiurai.repo.EmployeeRepository;
import com.backend.electroghiurai.repo.InternalOrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    private final EmailService emailService;
    @Autowired
    public EmployeeService(EmployeeRepository empRep,EmailService emailService){
        this.repository=empRep;
        this.emailService=emailService;
    }
    public Employee registerNewEmployee(Employee emp){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Employee record = repository.findByUsername(emp.getUsername());
        if(record !=null){
            throw new UsernameExistsException("Username already exists.");
        }
        emp.setPassword(encoder.encode(emp.getPassword()));
        return repository.save(emp);
    }
    public Employee getEmployee(String username, String password){
        Employee record = repository.findByUsername(username);
        if(record == null){
            throw new UsernameNotFoundException("Username doesn't exist.");
        }
        BCryptPasswordEncoder matcher = new BCryptPasswordEncoder();
        if(!matcher.matches(password, record.getPassword())){
            throw new IncorrectPasswordException("Username and password don't match.");
        }
        return record;
    }

    public Employee generateNewEmployee(EmployeeForm userData){
        Employee newEmployee = new Employee();
        Long id = repository.getNextEmpId();
        String username = generateUsername(userData.getFirstName(),userData.getLastName(),id);
        newEmployee.setUsername(username);
        String password = generatePassword();
        newEmployee.setPassword(password);
        newEmployee.setEmail(userData.getEmail());
        newEmployee.setPosition((long)2);
        newEmployee.setCountryOfOrigin(userData.getCountryOfOrigin());
        newEmployee.setDateOfBirth(userData.getDateOfBirth());
        newEmployee.setFirstName(userData.getFirstName());
        newEmployee.setLastName(userData.getLastName());
        emailService.sendAccountEmail(newEmployee.getEmail(),newEmployee.getUsername(),newEmployee.getPassword());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newEmployee.setPassword(encoder.encode(newEmployee.getPassword()));
        return repository.save(newEmployee);
    }
    private String generatePassword() {
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?").toCharArray();
        return RandomStringUtils.random( 16, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
    }
    private String generateUsername(String firstName,String lastName,Long id){
        return firstName + "_" + lastName + id;
    }

    public List<Employee> getAllJuniors(){
        return repository.findAllByPosition((long)2);
    }

    public List<Employee> getAllSeniors(){
        return repository.findAllByPosition((long)3);
    }


}
