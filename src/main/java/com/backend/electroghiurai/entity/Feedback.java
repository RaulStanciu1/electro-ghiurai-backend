package com.backend.electroghiurai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback")
public class Feedback {
    @Id
    @Column(name="f_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @Column(name= "description")
    private String description;

    @Column(name="rating")
    private Long rating;

    @Column(name = "user_id")
    private Long userId;
}
