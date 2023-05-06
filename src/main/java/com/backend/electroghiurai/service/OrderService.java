package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.Order;
import com.backend.electroghiurai.repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrdersRepository repository;
    public Order makeNewOrder(Order order,Long customerId){
        order.setCustomerId(customerId);
        return repository.save(order);
    }

    public List<Order> getAllOrders(Long id) {
        return repository.findAllByCustomerId(id);
    }

    public Order getOrderById(Long id){
        return repository.findByOrderId(id);
    }
}
