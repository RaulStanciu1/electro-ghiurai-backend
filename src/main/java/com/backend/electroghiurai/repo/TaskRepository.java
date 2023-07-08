package com.backend.electroghiurai.repo;

import com.backend.electroghiurai.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByAssignedEmpAndAndTaskStatus(Long assignedEmp, Integer taskStatus);
    Task findByTaskNr(Long taskNr);
    List<Task> findAllByAssignedEmpAndAndTaskStatusGreaterThan(Long empId, Integer taskStatus);

    @Query(value = "Select count(*) FROM TASKS WHERE ASSIGNED_EMP = :#{#id}",nativeQuery = true)
    Integer getTotalTasks(@Param("id") Long id);

    @Query(value = "Select count(*) FROM TASKS WHERE ASSIGNED_EMP = :#{#id} AND T_STATUS > 1",nativeQuery = true)
    Integer getTotalTasksCompleted(@Param("id") Long id);

    @Query(value = "Select count(*) FROM TASKS WHERE ASSIGNED_EMP = :#{#id} AND T_STATUS = 1",nativeQuery = true)
    Integer getTotalTasksAssigned(@Param("id") Long id);

    @Query(value = "select count(*) FROM TASKS WHERE ASSIGNED_EMP = :#{#id} AND T_STATUS = 2",nativeQuery = true)
    Integer getTotalTasksCompletedInTime(@Param("id") Long id);

    @Query(value = "select count(*) FROM TASKS WHERE ASSIGNED_EMP = :#{#id} AND T_STATUS = 3",nativeQuery = true)
    Integer getTotalTasksCompletedLate(@Param("id") Long id);
}
