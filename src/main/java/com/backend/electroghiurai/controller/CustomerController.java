package com.backend.electroghiurai.controller;

import com.backend.electroghiurai.entity.*;
import com.backend.electroghiurai.service.CustomerService;
import com.backend.electroghiurai.service.FeedbackService;
import com.backend.electroghiurai.service.OrderService;
import com.backend.electroghiurai.service.RemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final FeedbackService feedbackService;
    private final OrderService orderService;
    private final RemarkService remarkService;
    @Autowired
    public CustomerController(CustomerService c, FeedbackService f, OrderService o, RemarkService r){
        this.remarkService = r;
        this.customerService = c;
        this.orderService = o;
        this.feedbackService = f;
    }
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomerToDB(@RequestBody Customer customer){
        Customer record = customerService.registerCustomer(customer);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> loginCustomer(@RequestBody Map<String,String> userData){
        String username = userData.get("username");
        String password = userData.get("password");
        Customer loggedInCustomer = customerService.getCustomer(username,password);
        return new ResponseEntity<>(loggedInCustomer,HttpStatus.OK);
    }

    @PostMapping("/feedback/{id}")
    public ResponseEntity<Feedback> sendFeedback(@RequestBody Feedback feedback,@PathVariable Long id){
        Feedback record = feedbackService.saveFeedback(feedback,id);
        return new ResponseEntity<>(record,HttpStatus.CREATED);
    }

    @PostMapping("/order/{id}")
    public ResponseEntity<Order> sendNewOrder(@RequestBody Order order,@PathVariable Long id){
        Order record = orderService.makeNewOrder(order,id);
        return new ResponseEntity<>(record,HttpStatus.CREATED);
    }
    @PostMapping("/order/remark/{id}")
    public ResponseEntity<String> sendRemark(@RequestBody List<Remark> remark, @PathVariable Long id){
        remarkService.sendRemark(remark,id);
        return new ResponseEntity<>("Remark sent successfully.",HttpStatus.CREATED);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        Order record = orderService.getOrderById(id);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }

    @GetMapping("{id}/order")
    public ResponseEntity<List<Order>> getAllOrdersByCustomer(@PathVariable Long id){
        List<Order> records = orderService.getAllOrders(id);
        return new ResponseEntity<>(records,HttpStatus.OK);
    }

    @GetMapping("/order/remark/{id}")
    public ResponseEntity<List<Remark>> getRemarksByOrder(@PathVariable Long id){
        List<Remark> records = remarkService.getAllRemarks(id);
        return new ResponseEntity<>(records,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerInfo(@PathVariable Long id){
        Customer record = customerService.getCustomerById(id);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }


}
