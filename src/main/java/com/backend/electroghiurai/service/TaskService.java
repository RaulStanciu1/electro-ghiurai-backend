package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.InternalOrder;
import com.backend.electroghiurai.entity.Task;
import com.backend.electroghiurai.repo.InternalOrderRepository;
import com.backend.electroghiurai.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final InternalOrderRepository internalOrderRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository,InternalOrderRepository internalOrderRepository){
        this.taskRepository=taskRepository;
        this.internalOrderRepository=internalOrderRepository;
    }
    public void createFunctionTask(Long internalOrderId,Long functionDev){
        Task newTask = new Task();
        newTask.setTaskStatus(1);
        newTask.setTaskType(1);
        newTask.setAssignedEmp(functionDev);
        newTask.setInternalOrder(internalOrderId);
        taskRepository.save(newTask);
    }
    public void createSoftwareTask(Long internalOrderId,Long softwareDev){
        Task newTask = new Task();
        newTask.setTaskStatus(1);
        newTask.setTaskType(2);
        newTask.setAssignedEmp(softwareDev);
        newTask.setInternalOrder(internalOrderId);
        taskRepository.save(newTask);
    }
    public void createReviewTask(Long internalOrderId,Long reviewer){
        Task newTask = new Task();
        newTask.setTaskStatus(1);
        newTask.setTaskType(3);
        newTask.setInternalOrder(internalOrderId);
        newTask.setAssignedEmp(reviewer);
    }
    public List<Task> getCurrentlyAssignedTasks(Long empId){
        return taskRepository.findAllByAssignedEmpAndAndTaskStatus(empId,1);
    }

    public Task uploadSpec(Long taskId, MultipartFile specFile) throws IOException {
        Task completedTask = taskRepository.findByTaskNr(taskId);
        InternalOrder internalOrder = internalOrderRepository.findByInternalOrder(completedTask.getInternalOrder());
        completedTask.setTaskStatus(2);
        internalOrder.setInternalStatus((long)3);
        internalOrder.setSpec(specFile.getBytes());
        internalOrderRepository.save(internalOrder);
        return taskRepository.save(completedTask);
    }

    public Task uploadCode(Long taskId, MultipartFile codeFile) throws IOException{
        Task completedTask = taskRepository.findByTaskNr(taskId);
        InternalOrder internalOrder = internalOrderRepository.findByInternalOrder(completedTask.getInternalOrder());
        completedTask.setTaskStatus(2);
        internalOrder.setInternalStatus((long)5);
        internalOrder.setCode(codeFile.getBytes());
        internalOrderRepository.save(internalOrder);
        return taskRepository.save(completedTask);
    }

    public Task uploadFinalCode(Long taskId, MultipartFile codeFile) throws IOException{
        Task completedTask = taskRepository.findByTaskNr(taskId);
        InternalOrder internalOrder = internalOrderRepository.findByInternalOrder(completedTask.getInternalOrder());
        completedTask.setTaskStatus(2);
        internalOrder.setInternalStatus((long)7);
        internalOrder.setCode(codeFile.getBytes());
        internalOrderRepository.save(internalOrder);
        return taskRepository.save(completedTask);
    }
}