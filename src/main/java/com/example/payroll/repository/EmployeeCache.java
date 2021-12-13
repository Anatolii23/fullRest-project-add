package com.example.payroll.repository;

import com.example.payroll.config.CacheConfig;
import com.example.payroll.rest.dto.EmployeeDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EmployeeCache{
    @Cacheable(key = "#id", cacheManager = CacheConfig.Employee_DTO_Cache_Manager,
            cacheNames = CacheConfig.Employee_DTO_Cache_Name)
    public Optional<EmployeeDto> getEmployee(Long id) {
        return Optional.empty();
    }

    @CachePut(key = "#employeeDto.id", cacheManager = CacheConfig.Employee_DTO_Cache_Manager,
            cacheNames = CacheConfig.Employee_DTO_Cache_Name)
    public void saveEmployeeInCache(EmployeeDto employeeDto) {
    }

    @CacheEvict(key = "#id", cacheManager = CacheConfig.Employee_DTO_Cache_Manager,
            cacheNames = CacheConfig.Employee_DTO_Cache_Name)
    public void deleteEmployeeFromCache(Long id) {
    }
}
