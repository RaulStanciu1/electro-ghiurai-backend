package com.backend.electroghiurai.repo;

import com.backend.electroghiurai.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByUsername(String username);
    @Query(value = "select GET_NEXT_EMP_ID() from dual",nativeQuery = true)
    Long getNextEmpId();
    List<Employee> findAllByPosition(Long Position);
}
