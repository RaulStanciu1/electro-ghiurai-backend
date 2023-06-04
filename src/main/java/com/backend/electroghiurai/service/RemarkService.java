package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.InternalOrder;
import com.backend.electroghiurai.entity.Remark;
import com.backend.electroghiurai.repo.InternalOrderRepository;
import com.backend.electroghiurai.repo.RemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemarkService {
    @Autowired
    private RemarkRepository repository;
    @Autowired
    private InternalOrderRepository internalOrderRepository;
    public void sendRemark(List<Remark> remark, Long orderId){
        for(Remark r:remark){
            r.setOrderId(orderId);
        }
        repository.saveAll(remark);
    }

    public List<Remark> getAllRemarks(Long orderId){
        return repository.findAllByOrderId(orderId);
    }

    public List<Remark> getAllRemarksByInternal(Long internalId){
        InternalOrder internalOrder = internalOrderRepository.findByInternalOrder(internalId);
        Long orderId = internalOrder.getOrderId();
        return repository.findAllByOrderId(orderId);
    }
}
