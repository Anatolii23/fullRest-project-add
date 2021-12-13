package com.example.payroll.repository;

import com.example.payroll.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("from Customer c " +
            "where " +
            "   concat(c.lastName, ' ', c.firstName,' ', c.address) like concat('%', :name, '%')")
    List<Customer> findByNameAndAddress(@Param("name") String name);
}
