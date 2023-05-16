package com.backend.electroghiurai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tasks")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TASK_NR")
    private Long taskNr;
    @Column(name = "internal_order")
    private Long internalOrder;
    @Column(name = "assigned_emp")
    private Long assignedEmp;
    @Column(name="task_status")
    private Integer taskStatus;
    @Column(name = "task_type")
    private Integer taskType;
}
