package com.backend.electroghiurai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chart {
    private OrderChart orderChart;
    private UserChart userchart;
}
