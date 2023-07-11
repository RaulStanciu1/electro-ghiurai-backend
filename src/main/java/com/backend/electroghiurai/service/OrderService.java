package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.InternalOrder;
import com.backend.electroghiurai.entity.Order;
import com.backend.electroghiurai.entity.OrderChart;
import com.backend.electroghiurai.repo.InternalOrderRepository;
import com.backend.electroghiurai.repo.OrdersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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
    public void assignFunction(Long internalOrderId, Long functionDev, Date deadline){
        InternalOrder record = internalOrderRepository.findByInternalOrder(internalOrderId);
        record.setFunctionDev(functionDev);
        record.setInternalStatus((long)2);
        internalOrderRepository.save(record);
        taskService.createFunctionTask(internalOrderId,functionDev,deadline);
    }
    @Transactional
    public void assignSoftware(Long internalOrderId, Long softwareDev, Date deadline){
        InternalOrder record = internalOrderRepository.findByInternalOrder(internalOrderId);
        record.setSoftwareDev(softwareDev);
        record.setInternalStatus((long)4);
        internalOrderRepository.save(record);
        taskService.createSoftwareTask(internalOrderId,softwareDev,deadline);
    }
    @Transactional
    public void assignReviewer(Long internalOrderId, Long reviewer, Date deadline){
        InternalOrder record = internalOrderRepository.findByInternalOrder(internalOrderId);
        record.setReviewer(reviewer);
        record.setInternalStatus((long)6);
        internalOrderRepository.save(record);
        taskService.createReviewTask(internalOrderId,reviewer,deadline);
    }

    public Order finishOrder(Long orderId){
        Order record = ordersRepository.findByOrderId(orderId);
        record.setOrderStatus((long)3);
        return ordersRepository.save(record);
    }

    public void addCode(Long orderId, byte[] finalCode) throws IOException {
        Order record = ordersRepository.findByOrderId(orderId);
        record.setCode(finalCode);
        ordersRepository.save(record);
    }

    public InternalOrder getInternalOrderByOrderId(Long id){
        return internalOrderRepository.findByOrderId(id);
    }

    public Order getOrder(Long internalOrderId){
        InternalOrder internalOrder = internalOrderRepository.findByInternalOrder(internalOrderId);
        return ordersRepository.findByOrderId(internalOrder.getOrderId());
    }

    public byte[] getSpec(Long orderId){
        InternalOrder internalOrder = internalOrderRepository.findByOrderId(orderId);
        return internalOrder.getSpec();
    }

    public OrderChart getOrderChart(){
        Long pendingOrders = ordersRepository.getPendingOrderCount();
        Long completedOrders = ordersRepository.getCompletedOrderCount();
        Long ordersInProgress = ordersRepository.getOrderInProgressCount();
        return new OrderChart(pendingOrders,ordersInProgress,completedOrders);
    }
}
