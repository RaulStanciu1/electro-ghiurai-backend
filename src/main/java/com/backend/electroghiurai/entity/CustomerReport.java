package com.backend.electroghiurai.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReport {
    private int total_users;
    private int total_customers;
    private String top_3_countries;
    private int total_orders;
    private int pending_orders;
    private int accepted_orders;
    private int finished_orders;
    private int most_active_customer;
    private int most_active_customer_order_number;
    private int average_customer_age;
}
