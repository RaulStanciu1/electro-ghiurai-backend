package com.backend.electroghiurai.controller;

import com.backend.electroghiurai.entity.*;
import com.backend.electroghiurai.service.OrderService;
import com.backend.electroghiurai.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mng")
public class ManagerController {
    private final UserService userService;
    private final OrderService orderService;
    @Autowired
    public ManagerController(UserService userService, OrderService orderService){
            this.userService = userService;
            this.orderService = orderService;
    }
    @PostMapping("/new-emp")
    public ResponseEntity<User> createNewEmployee(@RequestBody EmployeeForm empForm){
        User newEmp = userService.generateNewEmployee(empForm);
        return new ResponseEntity<>(newEmp,HttpStatus.CREATED);
    }

    @GetMapping("/order/pending")
    public ResponseEntity<List<Order>> getPendingOrders(){
        List<Order> records = orderService.getAllPendingOrders();
        return new ResponseEntity<>(records,HttpStatus.OK);
    }
    @GetMapping("/order/accepted")
    public ResponseEntity<List<Order>> getAcceptedOrders(){
        List<Order> records = orderService.getAllAcceptedOrders();
        return new ResponseEntity<>(records,HttpStatus.OK);
    }
    @GetMapping("/order/finished")
    public ResponseEntity<List<Order>> getFinishedOrders(){
        List<Order> records = orderService.getAllFinishedOrders();
        return new ResponseEntity<>(records,HttpStatus.OK);
    }
    @PostMapping("/order/accept/{id}")
    @Transactional
    public ResponseEntity<InternalOrder> acceptOrder(@PathVariable Long id){
        InternalOrder record = orderService.acceptOrder(id);
        return new ResponseEntity<>(record,HttpStatus.ACCEPTED);
    }
    @GetMapping("/junior")
    public ResponseEntity<List<User>> getJuniorEmployees(){
        List<User> records = userService.getJuniorDevelopers();
        return new ResponseEntity<>(records,HttpStatus.OK);
    }
    @GetMapping("/senior")
    public ResponseEntity<List<User>> getSeniorEmployees(){
        List<User> records = userService.getSeniorDevelopers();
        return new ResponseEntity<>(records,HttpStatus.OK);
    }

    @GetMapping("/engineer")
    @Transactional
    public ResponseEntity<List<User>> getEmployees(){
        List<User> recordsJunior = userService.getJuniorDevelopers();
        List<User> recordsSenior = userService.getSeniorDevelopers();
        List<User> recordsEmployee = new ArrayList<>(recordsJunior);
        recordsEmployee.addAll(recordsSenior);
        return new ResponseEntity<>(recordsEmployee,HttpStatus.OK);
    }
    @PostMapping ("/order/{id}/assign/function/{function}")
    public ResponseEntity<String> assignFunctionDev(@PathVariable Long id, @PathVariable Long function, @RequestBody Map<String, Date> deadline){
        orderService.assignFunction(id,function,deadline.get("deadline"));
        return new ResponseEntity<>("Function assigned.",HttpStatus.OK);
    }
    @PostMapping("/order/{id}/assign/software/{software}")
    public ResponseEntity<String> assignSoftwareDev(@PathVariable Long id,@PathVariable Long software, @RequestBody Map<String,Date> deadline){
        orderService.assignSoftware(id,software,deadline.get("deadline"));
        return new ResponseEntity<>("Software assigned.",HttpStatus.OK);
    }
    @PostMapping("/order/{id}/assign/reviewer/{reviewer}")
    public ResponseEntity<String> assignReviewer(@PathVariable Long id,@PathVariable Long reviewer,@RequestBody Map<String,Date> deadline){
        orderService.assignReviewer(id,reviewer,deadline.get("deadline"));
        return new ResponseEntity<>("Reviewer assigned.",HttpStatus.OK);
    }
    @GetMapping("/order/internal/{id}")
    public ResponseEntity<InternalOrder> getInternalOrder(@PathVariable Long id){
        InternalOrder record = orderService.getInternalOrderByOrderId(id);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }
    @PostMapping("/finish/order/{id}")
    @Transactional
    public ResponseEntity<Order> finishOrder(@PathVariable Long id) throws IOException {
        byte[] finalCode = orderService.getInternalOrderByOrderId(id).getCode();
        orderService.addCode(id,finalCode);
        Order record = orderService.finishOrder(id);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }
    @GetMapping("/download/code/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long id){
        Order record = orderService.getOrderById(id);
        byte[] bytes = record.getCode();
        ByteArrayResource resource = new ByteArrayResource(bytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order-code");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resource);
    }
    @GetMapping("/download/spec/{id}")
    public ResponseEntity<ByteArrayResource> downloadSpec(@PathVariable Long id) {
        byte[] specBytes = orderService.getSpec(id);
        ByteArrayResource resource = new ByteArrayResource(specBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order-spec.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/report/customer")
    public ResponseEntity<String> getCustomerReport(){
        return new ResponseEntity<>(userService.getCustomerReport(),HttpStatus.OK);
    }
    @GetMapping("/get/employee/{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }

    @GetMapping("/report/employee")
    public ResponseEntity<String> getEmployeeReport(){
        return new ResponseEntity<>(userService.getEmployeeReport(),HttpStatus.OK);
    }

    @GetMapping("/get/employee/{id}/performance")
    public ResponseEntity<EmployeeInfo> getEmployeePerformance(@PathVariable Long id){
        return new ResponseEntity<>(userService.getEmployeePerformance(id),HttpStatus.OK);
    }
    @PostMapping("/promote/employee/{id}")
    public ResponseEntity<User>promoteEmployee(@PathVariable Long id){
        return new ResponseEntity<>(userService.promoteEmployee(id),HttpStatus.OK);
    }
    @PostMapping("/demote/employee/{id}")
    public ResponseEntity<User>demoteEmployee(@PathVariable Long id){
        return new ResponseEntity<>(userService.demoteEmployee(id),HttpStatus.OK);
    }

    @GetMapping("/chart")
    public ResponseEntity<Chart>getChart(){
        UserChart userChart = userService.getUserChart();
        OrderChart orderChart = orderService.getOrderChart();
        return new ResponseEntity<>(new Chart(orderChart, userChart),HttpStatus.OK);
    }
}
