package com.example.payroll.repository;

import com.example.payroll.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("from Order o " +
            "where " +
            "   concat(o.description, ' ', o.status) like concat('%', :name, '%')")
    List<Order> findByNameAndStatus(@Param("name") String name);
}
