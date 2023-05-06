package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.Remark;
import com.backend.electroghiurai.repo.RemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemarkService {
    @Autowired
    private RemarkRepository repository;
    public Remark sendRemark(Remark remark,Long orderId){
        remark.setOrderId(orderId);
        return repository.save(remark);
    }

    public List<Remark> getAllRemarks(Long orderId){
        return repository.findAllByOrderId(orderId);
    }
}
