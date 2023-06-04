package com.backend.electroghiurai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="tasks")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="t_nr")
    private Long taskNr;
    @Column(name = "internal_order")
    private Long internalOrder;
    @Column(name = "assigned_emp")
    private Long assignedEmp;
    @Column(name="t_status")
    private Integer taskStatus;
    @Column(name = "t_type")
    private Integer taskType;
    @Column(name = "deadline")
    private Date deadline;
}
