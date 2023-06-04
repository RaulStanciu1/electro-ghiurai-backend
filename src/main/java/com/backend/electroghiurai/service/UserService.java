package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.EmployeeForm;
import com.backend.electroghiurai.entity.User;
import com.backend.electroghiurai.exception.IncorrectPasswordException;
import com.backend.electroghiurai.exception.UsernameExistsException;
import com.backend.electroghiurai.repo.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    @Autowired
    public UserService(UserRepository repo,EmailService emailService){
        this.userRepository=repo;
        this.emailService=emailService;
    }
    public User registerUser(User user){
        if(usernameAlreadyExists(user.getUsername())){
            throw new UsernameExistsException("Username is already in use.");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }
    private boolean usernameAlreadyExists(String username){
        return userRepository.findByUsername(username) != null;
    }

    public User getUserById(Long id){
        return userRepository.findByUserId(id);
    }
    public User getUser(String username, String password){
        User record = userRepository.findByUsername(username);
        if(record == null){
            throw new UsernameNotFoundException("Username doesn't exist.");
        }
        BCryptPasswordEncoder matcher = new BCryptPasswordEncoder();
        if(!matcher.matches(password, record.getPassword())){
            throw new IncorrectPasswordException("Username and password don't match.");
        }
        return record;
    }

    public User generateNewEmployee(EmployeeForm userData){
        User newEmployee = new User();
        Long id = userRepository.getNextUserId();
        String username = generateUsername(userData.getFirstName(),userData.getLastName(),id);
        newEmployee.setUsername(username);
        String password = generatePassword();
        newEmployee.setPassword(password);
        newEmployee.setEmail(userData.getEmail());
        newEmployee.setPosition(2);
        newEmployee.setCountry(userData.getCountryOfOrigin());
        newEmployee.setDateOfBirth(userData.getDateOfBirth());
        newEmployee.setFirstName(userData.getFirstName());
        newEmployee.setLastName(userData.getLastName());
        emailService.sendAccountEmail(newEmployee.getEmail(),newEmployee.getUsername(),newEmployee.getPassword());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newEmployee.setPassword(encoder.encode(newEmployee.getPassword()));
        return userRepository.save(newEmployee);
    }
    private String generatePassword() {
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#()-_").toCharArray();
        return RandomStringUtils.random( 16, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
    }
    private String generateUsername(String firstName,String lastName,Long id){
        return firstName + "_" + lastName + id;
    }

    public List<User> getJuniorDevelopers(){
        return userRepository.findAllByPosition(2);
    }

    public List<User> getSeniorDevelopers(){
        return userRepository.findAllByPosition(3);
    }

    public String getCustomerReport(){
        return userRepository.generateCustomerReport();
    }

    public String getEmployeeReport(){
        return userRepository.generateEmployeeReport();
    }

    public User updateProfilePic(Long id,MultipartFile image) throws IOException {
        User record = userRepository.findByUserId(id);
        record.setProfilePic(image.getBytes());
        return userRepository.save(record);
    }

}
