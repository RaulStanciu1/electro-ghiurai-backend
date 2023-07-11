package com.backend.electroghiurai.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeInfo {
    private static final Float TOTAL_TASKS_MULTIPLIER = 0.1f;
    private static final Float TASKS_ASSIGNED_MULTIPLIER = 0.25f;
    private static final Float TASKS_COMPLETED_IN_TIME_MULTIPLIER = 0.5f;
    private static final Float TASKS_COMPLETED_LATE_MULTIPLIER = 0.35f;
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String countryOfBirth;
    private Integer position;
    private Integer totalTasksCompleted;
    private Integer totalTasksAssigned;
    private Integer totalTasks;
    private Integer tasksCompletedInTime;
    private Integer tasksCompletedLate;
    private Float performancePoints;
    public EmployeeInfo(Long id, String firstName, String lastName, String email, String countryOfBirth, Integer position,
                        Integer totalTasks, Integer totalTasksAssigned,Integer totalTasksCompleted,
                        Integer totalTasksCompletedInTime, Integer totalTasksCompletedLate, Float performancePoints){
        this.employeeId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.countryOfBirth = countryOfBirth;
        this.position = position;
        this.totalTasks = totalTasks;
        this.totalTasksAssigned = totalTasksAssigned;
        this.totalTasksCompleted = totalTasksCompleted;
        this.tasksCompletedInTime = totalTasksCompletedInTime;
        this.tasksCompletedLate  = totalTasksCompletedLate;
        this.performancePoints = performancePoints;
    }
    public static Float calcPerformancePoints(Integer totalTasks, Integer totalTasksAssigned, Integer totalTasksCompleted,
                                              Integer tasksCompletedInTime, Integer tasksCompletedLate){
        float performancePoints = 0f;
        performancePoints += totalTasks*TOTAL_TASKS_MULTIPLIER;
        performancePoints += totalTasksAssigned*TASKS_ASSIGNED_MULTIPLIER;
        performancePoints += tasksCompletedInTime*TASKS_COMPLETED_IN_TIME_MULTIPLIER;
        performancePoints += tasksCompletedLate*TASKS_COMPLETED_LATE_MULTIPLIER;
        if(totalTasks!=0 && (float) totalTasksCompleted /totalTasks > 0.8f){
            performancePoints += 2f;
        }
        if(tasksCompletedInTime!=0 && (float) totalTasksCompleted / tasksCompletedInTime > 0.8f){
            performancePoints +=2f;
        }
        return performancePoints;
    }
}
