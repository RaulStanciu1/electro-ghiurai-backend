package com.backend.electroghiurai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    @Autowired
    public EmailService(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }

    public void sendAccountEmail(String email, String username, String password) {
        String body = "Your new Username: " + username + "\n" + "Your Password: " + password;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your New Employee Account");
        message.setText(body);
        mailSender.send(message);
    }
}
