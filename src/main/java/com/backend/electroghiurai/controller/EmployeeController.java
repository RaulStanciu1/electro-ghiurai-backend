package com.backend.electroghiurai.controller;

import com.backend.electroghiurai.entity.InternalOrder;
import com.backend.electroghiurai.entity.Task;
import com.backend.electroghiurai.service.TaskService;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    private final TaskService taskService;
    @Autowired
    public EmployeeController(TaskService taskService){
        this.taskService=taskService;
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

}
