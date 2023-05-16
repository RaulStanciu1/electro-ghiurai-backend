package com.backend.electroghiurai.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "remarks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "remark_id")
    private Long id;
    @Column(name = "order_id")
    @JsonProperty("orderId")
    private Long orderId;
    @Column(name = "description")
    @JsonProperty("description")
    private String description;
}
