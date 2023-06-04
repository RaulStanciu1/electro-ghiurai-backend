package com.backend.electroghiurai.controller;

import com.backend.electroghiurai.entity.*;
import com.backend.electroghiurai.service.OrderService;
import com.backend.electroghiurai.service.RemarkService;
import com.backend.electroghiurai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {
    private final UserService userService;
    private final OrderService orderService;
    private final RemarkService remarkService;
    @Autowired
    public CustomerController(UserService u,OrderService o, RemarkService r){
        this.userService = u;
        this.orderService = o;
        this.remarkService = r;
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
    public ResponseEntity<User> getCustomerInfo(@PathVariable Long id){
        User record = userService.getUserById(id);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }

}
