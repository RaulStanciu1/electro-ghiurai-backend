package com.backend.electroghiurai.repo;

import com.backend.electroghiurai.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByCustomerId(Long customerId);
    Order findByOrderId(Long orderId);
    List<Order> findAllByOrderStatus(Long orderStatus);

    @Query(value = "select COUNT(*) FROM ORDERS WHERE O_STATUS = 1",nativeQuery = true)
    Long getPendingOrderCount();

    @Query(value = "select COUNT(*) FROM ORDERS WHERE O_STATUS = 2",nativeQuery = true)
    Long getOrderInProgressCount();

    @Query(value = "select COUNT(*) FROM ORDERS WHERE O_STATUS = 3",nativeQuery = true)
    Long getCompletedOrderCount();
}
