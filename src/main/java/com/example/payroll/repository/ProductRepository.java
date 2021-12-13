package com.example.payroll.repository;

import com.example.payroll.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    @Query("from Product p " +
            "where p.name like concat('%', :name, '%')")
    List<Product> findByNameList(@Param("name") String name);
}
