package com.backend.electroghiurai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderChart {
    private Long pendingOrders;
    private Long ordersInProgress;
    private Long completedOrders;
    public OrderChart(Long pendingOrders,Long ordersInProgress,Long completedOrders){
        this.pendingOrders = pendingOrders;
        this.ordersInProgress=ordersInProgress;
        this.completedOrders = completedOrders;
    }
}
