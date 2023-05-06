package com.backend.electroghiurai.repo;

import com.backend.electroghiurai.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByUsername(String username);
}
