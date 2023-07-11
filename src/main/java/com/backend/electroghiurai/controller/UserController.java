package com.backend.electroghiurai.controller;

import com.backend.electroghiurai.entity.Feedback;
import com.backend.electroghiurai.entity.User;
import com.backend.electroghiurai.service.FeedbackService;
import com.backend.electroghiurai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final FeedbackService feedbackService;
    @Autowired
    public UserController(UserService userService,FeedbackService feedbackService){
        this.userService=userService;
        this.feedbackService=feedbackService;
    }
    @PostMapping("/register")
    public ResponseEntity<User> registerCustomerToDB(@RequestBody User customer){
        customer.setPosition(1);
        User record = userService.registerUser(customer);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<User> loginCustomer(@RequestBody Map<String,String> userData){
        String username = userData.get("username");
        String password = userData.get("password");
        User loggedInUser = userService.getUser(username,password);
        return new ResponseEntity<>(loggedInUser,HttpStatus.OK);
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getTopFeedback(){
        return new ResponseEntity<>(feedbackService.getBestFeedback(),HttpStatus.OK);
    }

    @PostMapping("/profile-pic/{id}")
    public ResponseEntity<User> uploadProfilePic(@PathVariable Long id, @RequestParam("profilePic") MultipartFile image)throws IOException{
        return new ResponseEntity<>(userService.uploadProfilePic(id,image),HttpStatus.OK);
    }

    @GetMapping("/profile-pic/{id}")
    public ResponseEntity<byte[]> getProfilePic(@PathVariable Long id){
        return new ResponseEntity<>(userService.getProfilePic(id),HttpStatus.OK);
    }
}
