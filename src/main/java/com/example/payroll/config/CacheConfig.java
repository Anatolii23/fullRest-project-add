package com.example.payroll.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    public static final String Customer_DTO_Cache_Manager = "customerResponseCacheManager";
    public static final String Customer_DTO_Cache_Name = "customerResponseCache";
    public static final String Employee_DTO_Cache_Manager = "EmployeeResponseCacheManager";
    public static final String Employee_DTO_Cache_Name = "EmployeeResponseCache";
    public static final String Product_DTO_Cache_Manager = "ProductResponseCacheManager";
    public static final String Product_DTO_Cache_Name = "ProductResponseCache";
    @Bean(Customer_DTO_Cache_Manager)
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().expireAfterWrite(120, TimeUnit.SECONDS);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setCacheNames(Collections.singleton(Customer_DTO_Cache_Name));
        return cacheManager;
    }
    @Bean(Employee_DTO_Cache_Manager)
    public CacheManager cacheManager1() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine =Caffeine.newBuilder().expireAfterWrite(120, TimeUnit.SECONDS);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setCacheNames(Collections.singleton(Employee_DTO_Cache_Name));
        return cacheManager;
    }
    @Bean(Product_DTO_Cache_Manager)
    public CacheManager cacheManager2() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().expireAfterWrite(120, TimeUnit.SECONDS);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setCacheNames(Collections.singleton(Product_DTO_Cache_Name));
        return cacheManager;
    }
}
