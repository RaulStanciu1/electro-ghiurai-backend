package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.InternalOrder;
import com.backend.electroghiurai.entity.Task;
import com.backend.electroghiurai.repo.InternalOrderRepository;
import com.backend.electroghiurai.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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
    public void createFunctionTask(Long internalOrderId, Long functionDev, Date deadline){
        Task newTask = new Task();
        newTask.setTaskStatus(1);
        newTask.setTaskType(1);
        newTask.setAssignedEmp(functionDev);
        newTask.setInternalOrder(internalOrderId);
        newTask.setDeadline(deadline);
        taskRepository.save(newTask);
    }
    public void createSoftwareTask(Long internalOrderId,Long softwareDev, Date deadline){
        Task newTask = new Task();
        newTask.setTaskStatus(1);
        newTask.setTaskType(2);
        newTask.setAssignedEmp(softwareDev);
        newTask.setInternalOrder(internalOrderId);
        newTask.setDeadline(deadline);
        taskRepository.save(newTask);
    }
    public void createReviewTask(Long internalOrderId,Long reviewer, Date deadline){
        Task newTask = new Task();
        newTask.setTaskStatus(1);
        newTask.setTaskType(3);
        newTask.setInternalOrder(internalOrderId);
        newTask.setDeadline(deadline);
        newTask.setAssignedEmp(reviewer);
        taskRepository.save(newTask);
    }
    public List<Task> getCurrentlyAssignedTasks(Long empId){
        return taskRepository.findAllByAssignedEmpAndAndTaskStatus(empId,1);
    }

    public Task uploadSpec(Long taskId, MultipartFile specFile) throws IOException {
        int status = 2;
        Task completedTask = taskRepository.findByTaskNr(taskId);
        InternalOrder internalOrder = internalOrderRepository.findByInternalOrder(completedTask.getInternalOrder());
        if(completedTask.getDeadline().before(new Date())){
            status = 3;
        }
        completedTask.setTaskStatus(status);
        internalOrder.setInternalStatus((long)3);
        internalOrder.setSpec(specFile.getBytes());
        internalOrderRepository.save(internalOrder);
        return taskRepository.save(completedTask);
    }

    public byte[] getSpec(Long taskId){
        Task oldTask = taskRepository.findByTaskNr(taskId);
        InternalOrder internalOrder = internalOrderRepository.findByInternalOrder(oldTask.getInternalOrder());
        return internalOrder.getSpec();
    }


    public Task uploadCode(Long taskId, MultipartFile codeFile) throws IOException{
        int status = 2;
        Task completedTask = taskRepository.findByTaskNr(taskId);
        InternalOrder internalOrder = internalOrderRepository.findByInternalOrder(completedTask.getInternalOrder());
        if(completedTask.getDeadline().before(new Date())){
            status = 3;
        }
        completedTask.setTaskStatus(status);
        internalOrder.setInternalStatus((long)5);
        internalOrder.setCode(codeFile.getBytes());
        internalOrderRepository.save(internalOrder);
        return taskRepository.save(completedTask);
    }

    public byte[] getCode(Long taskId){
        Task oldTask = taskRepository.findByTaskNr(taskId);
        InternalOrder internalOrder = internalOrderRepository.findByInternalOrder(oldTask.getInternalOrder());
        return internalOrder.getCode();
    }

    public Task declareReviewStatus(Long taskId, Integer reviewStatus){
        int status = 2;
        long tmpReviewStatus = 7;
        Task completedTask = taskRepository.findByTaskNr(taskId);
        InternalOrder internalOrder = internalOrderRepository.findByInternalOrder(completedTask.getInternalOrder());
        if(completedTask.getDeadline().before(new Date())){
            status = 3;
        }
        completedTask.setTaskStatus(status);
        switch (reviewStatus) {
            case 2 -> tmpReviewStatus = 8;
            case 3 -> tmpReviewStatus = 9;
        }
        internalOrder.setInternalStatus(tmpReviewStatus);
        internalOrderRepository.save(internalOrder);
        return taskRepository.save(completedTask);
    }
}
