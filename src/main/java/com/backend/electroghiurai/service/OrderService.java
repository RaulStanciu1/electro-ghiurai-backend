package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.InternalOrder;
import com.backend.electroghiurai.entity.Order;
import com.backend.electroghiurai.repo.InternalOrderRepository;
import com.backend.electroghiurai.repo.OrdersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final InternalOrderRepository internalOrderRepository;
    private final TaskService taskService;
    @Autowired
    public OrderService(OrdersRepository ordersRepository, InternalOrderRepository internalOrderRepository,TaskService taskService){
        this.internalOrderRepository=internalOrderRepository;
        this.ordersRepository=ordersRepository;
        this.taskService= taskService;
    }
    public Order makeNewOrder(Order order,Long customerId){
        order.setCustomerId(customerId);
        order.setOrderStatus((long)1);
        return ordersRepository.save(order);
    }

    public List<Order> getAllOrders(Long id) {
        return ordersRepository.findAllByCustomerId(id);
    }

    public Order getOrderById(Long id){
        return ordersRepository.findByOrderId(id);
    }
    public List<Order> getAllPendingOrders(){
        return ordersRepository.findAllByOrderStatus((long)1);
    }
    public List<Order> getAllAcceptedOrders(){
        return ordersRepository.findAllByOrderStatus((long)2);
    }
    public List<Order> getAllFinishedOrders(){
        return ordersRepository.findAllByOrderStatus((long)3);
    }
    public InternalOrder acceptOrder(Long orderId){
        InternalOrder newOrder = new InternalOrder();
        newOrder.setOrderId(orderId);
        newOrder.setInternalStatus((long)1);
        newOrder.setCode(null);
        newOrder.setSpec(null);
        newOrder.setFunctionDev(null);
        newOrder.setSoftwareDev(null);
        newOrder.setReviewer(null);
        Order record = ordersRepository.findByOrderId(orderId);
        record.setOrderStatus((long)2);
        ordersRepository.save(record);
        return internalOrderRepository.save(newOrder);
    }
    @Transactional
    public void assignFunction(Long internalOrderId, Long functionDev){
        InternalOrder record = internalOrderRepository.findByInternalOrder(internalOrderId);
        record.setFunctionDev(functionDev);
        record.setInternalStatus((long)2);
        internalOrderRepository.save(record);
        taskService.createFunctionTask(internalOrderId,functionDev);
    }
    @Transactional
    public void assignSoftware(Long internalOrderId, Long softwareDev){
        InternalOrder record = internalOrderRepository.findByInternalOrder(internalOrderId);
        record.setSoftwareDev(softwareDev);
        record.setInternalStatus((long)3);
        internalOrderRepository.save(record);
        taskService.createSoftwareTask(internalOrderId,softwareDev);
    }
    @Transactional
    public void assignReviewer(Long internalOrderId, Long reviewer){
        InternalOrder record = internalOrderRepository.findByInternalOrder(internalOrderId);
        record.setReviewer(reviewer);
        internalOrderRepository.save(record);
        taskService.createReviewTask(internalOrderId,reviewer);
    }

    public Order finishOrder(Long orderId){
        Order record = ordersRepository.findByOrderId(orderId);
        record.setOrderStatus((long)3);
        return ordersRepository.save(record);
    }

    public void addCode(Long orderId, MultipartFile code) throws IOException {
        Order record = ordersRepository.findByOrderId(orderId);
        record.setCode(code.getBytes());
    }

    public InternalOrder getInternalOrderByOrderId(Long id){
        return internalOrderRepository.findByOrderId(id);
    }
}
