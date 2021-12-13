package com.example.payroll.repository;

import com.example.payroll.config.CacheConfig;
import com.example.payroll.rest.dto.ProductDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductCache {
    @Cacheable(key = "#id", cacheManager = CacheConfig.Product_DTO_Cache_Manager,
            cacheNames = CacheConfig.Product_DTO_Cache_Name)
    public Optional<ProductDto> getProduct(Long id) {
        return Optional.empty();
    }

    @CachePut(key = "#productDto.id", cacheManager = CacheConfig.Product_DTO_Cache_Manager,
            cacheNames = CacheConfig.Product_DTO_Cache_Name)
    public void saveProductInCache(ProductDto productDto) {
    }

    @CacheEvict(key = "#id", cacheManager = CacheConfig.Product_DTO_Cache_Manager,
            cacheNames = CacheConfig.Product_DTO_Cache_Name)
    public void deleteProductFromCache(Long id) {
    }
}
