package com.backend.electroghiurai.controller;

import com.backend.electroghiurai.entity.Employee;
import com.backend.electroghiurai.entity.EmployeeForm;
import com.backend.electroghiurai.entity.InternalOrder;
import com.backend.electroghiurai.entity.Order;
import com.backend.electroghiurai.service.EmployeeService;
import com.backend.electroghiurai.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mng")
public class ManagerController {
    private final EmployeeService employeeService;
    private final OrderService orderService;
    @Autowired
    public ManagerController(EmployeeService employeeService, OrderService orderService){
            this.employeeService=employeeService;
            this.orderService = orderService;
    }
    @PostMapping("/register")
    public ResponseEntity<Employee> registerNewEmployee(@RequestBody Employee emp){
        Employee record = employeeService.registerNewEmployee(emp);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<Employee> loginEmployee(@RequestBody Map<String,String> empData){
        String username = empData.get("username");
        String password = empData.get("password");
        Employee loggedEmp = employeeService.getEmployee(username,password);
        return new ResponseEntity<>(loggedEmp,HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Employee> createNewEmployee(@RequestBody EmployeeForm empForm){
        Employee newEmp = employeeService.generateNewEmployee(empForm);
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
    public ResponseEntity<List<Employee>> getJuniorEmployees(){
        List<Employee> records = employeeService.getAllJuniors();
        return new ResponseEntity<>(records,HttpStatus.OK);
    }
    @GetMapping("/senior")
    public ResponseEntity<List<Employee>> getSeniorEmployees(){
        List<Employee> records = employeeService.getAllSeniors();
        return new ResponseEntity<>(records,HttpStatus.OK);
    }
    @PostMapping ("/order/{id}/assign/function/{function}")
    public ResponseEntity<String> assignFunctionDev(@PathVariable Long id,@PathVariable Long function){
        orderService.assignFunction(id,function);
        return new ResponseEntity<>("Function assigned.",HttpStatus.OK);
    }
    @PostMapping("/order/{id}/assign/software/{software}")
    public ResponseEntity<String> assignSoftwareDev(@PathVariable Long id,@PathVariable Long software){
        orderService.assignSoftware(id,software);
        return new ResponseEntity<>("Software assigned.",HttpStatus.OK);
    }
    @PostMapping("/order/{id}/assign/reviewer/{reviewer}")
    public ResponseEntity<String> assignReviewer(@PathVariable Long id,@PathVariable Long reviewer){
        orderService.assignReviewer(id,reviewer);
        return new ResponseEntity<>("Reviewer assigned.",HttpStatus.OK);
    }
    @GetMapping("/order/internal/{id}")
    public ResponseEntity<InternalOrder> getInternalOrder(@PathVariable Long id){
        InternalOrder record = orderService.getInternalOrderByOrderId(id);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }
    @PostMapping("/finish/order/{id}")
    @Transactional
    public ResponseEntity<Order> finishOrder(@RequestParam("file") MultipartFile code, @PathVariable Long id) throws IOException {
        orderService.addCode(id,code);
        Order record = orderService.finishOrder(id);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }
    @GetMapping("/code/{id}")
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
}
