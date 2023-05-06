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
    @Column(name="feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @Column(name="feedback_comment")
    private String comment;

    @Column(name="rating")
    private Long rating;

    @Column(name = "customer")
    private Long customer;
}
