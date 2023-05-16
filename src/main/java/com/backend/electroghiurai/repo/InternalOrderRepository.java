package com.backend.electroghiurai.repo;

import com.backend.electroghiurai.entity.InternalOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternalOrderRepository extends JpaRepository<InternalOrder,Long> {
    InternalOrder findByInternalOrder(Long id);
    InternalOrder findByOrderId(Long id);
}
