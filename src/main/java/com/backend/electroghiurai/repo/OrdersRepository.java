package com.backend.electroghiurai.repo;

import com.backend.electroghiurai.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByCustomerId(Long customerId);
    Order findByOrderId(Long orderId);
    List<Order> findAllByOrderStatus(Long orderStatus);
}
