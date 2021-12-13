package com.example.payroll.repository;

import com.example.payroll.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("from Employee e " +
            "where " +
            "   concat(e.lastName, ' ', e.firstName,' ', e.role) like concat('%', :name, '%')")
    List<Employee> findByNameAndRole(@Param("name") String name);
}
