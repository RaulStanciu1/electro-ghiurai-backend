package com.backend.electroghiurai.repo;

import com.backend.electroghiurai.entity.Remark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RemarkRepository extends JpaRepository<Remark,Long> {
    List<Remark> findAllByOrderId(Long order);
}
