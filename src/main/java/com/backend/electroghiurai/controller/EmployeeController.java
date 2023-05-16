package com.backend.electroghiurai.controller;

import com.backend.electroghiurai.entity.Customer;
import com.backend.electroghiurai.entity.Employee;
import com.backend.electroghiurai.entity.InternalOrder;
import com.backend.electroghiurai.entity.Task;
import com.backend.electroghiurai.service.EmployeeService;
import com.backend.electroghiurai.service.TaskService;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
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
@RequestMapping("/emp")
public class EmployeeController {
    private final TaskService taskService;
    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController(TaskService taskService,EmployeeService employeeService){
        this.taskService=taskService;
        this.employeeService = employeeService;
    }
    @PostMapping("/login")
    public ResponseEntity<Employee> loginEmployee(@RequestBody Map<String,String> empData){
        String username = empData.get("username");
        String password = empData.get("password");
        Employee loggedEmp = employeeService.getEmployee(username,password);
        return new ResponseEntity<>(loggedEmp,HttpStatus.OK);
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity<List<Task>> getAssignedTasks(@PathVariable Long id){
        List<Task> records = taskService.getCurrentlyAssignedTasks(id);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    @PostMapping("/spec/{id}")
    @Transactional
    public ResponseEntity<Task> uploadSpec(@PathVariable Long id, @RequestParam("file")MultipartFile spec) throws IOException {
        Task record = taskService.uploadSpec(id,spec);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }
    @PostMapping("/code/{id}")
    @Transactional
    public ResponseEntity<Task> uploadCode(@PathVariable Long id, @RequestParam("file") MultipartFile code) throws IOException {
        Task record = taskService.uploadCode(id,code);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }

    @PostMapping("/review/{id}")
    @Transactional
    public ResponseEntity<Task> uploadFinalCode(@PathVariable Long id, @RequestParam("file") MultipartFile finalCode) throws IOException{
        Task record = taskService.uploadFinalCode(id, finalCode);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }

    @GetMapping("/download/spec/{id}")
    public ResponseEntity<ByteArrayResource> downloadSpec(@PathVariable Long id) throws IOException {
        byte[] specBytes = taskService.getSpec(id);
        ByteArrayResource resource = new ByteArrayResource(specBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order-spec.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/download/code/{id}")
    public ResponseEntity<ByteArrayResource> downloadCode(@PathVariable Long id) throws IOException {
        byte[] specBytes = taskService.getCode(id);
        ByteArrayResource resource = new ByteArrayResource(specBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order-code.zip");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resource);
    }
    @GetMapping("/download/final-code/{id}")
    public ResponseEntity<ByteArrayResource> downloadFinalCode(@PathVariable Long id) throws IOException {
        byte[] specBytes = taskService.getCode(id);
        ByteArrayResource resource = new ByteArrayResource(specBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order-final-code.zip");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resource);
    }

}
