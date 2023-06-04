package com.backend.electroghiurai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "INTERNAL_ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="io_id")
    private Long internalOrder;
    @Column(name="order_id")
    private Long orderId;
    @Column(name = "io_status")
    private Long internalStatus;
    @Column(name="spec")
    @Lob
    private byte[] spec;
    @Column(name = "code")
    @Lob
    private byte[] code;
    @Column(name="function_dev")
    private Long functionDev;
    @Column(name = "software_dev")
    private Long softwareDev;
    @Column(name = "reviewer")
    private Long reviewer;
}
