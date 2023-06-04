package com.backend.electroghiurai.repo;

import com.backend.electroghiurai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByUserId(Long userId);
    @Query(value = "select GET_NEXT_USER_ID() from dual",nativeQuery = true)
    Long getNextUserId();
    List<User> findAllByPosition(Integer position);

    @Query(value = "select CUSTOMER_REPORT.GENERATE() from dual",nativeQuery = true)
    String generateCustomerReport();

    @Query(value = "select EMPLOYEE_REPORT.GENERATE() from dual",nativeQuery = true)
    String generateEmployeeReport();
}
