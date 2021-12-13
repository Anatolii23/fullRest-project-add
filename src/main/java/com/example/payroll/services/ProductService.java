package com.example.payroll.services;

import com.example.payroll.entity.Product;
import com.example.payroll.errors.ProductNotFoundException;
import com.example.payroll.repository.ProductRepository;
import com.example.payroll.rest.dto.ProductDto;
import com.example.payroll.until.EntityDtoMapper;
import com.example.payroll.until.ProductModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductModelAssembler productModelAssembler;

    public List<EntityModel<ProductDto>> getAllProductsModel() {
        return productRepository.findAll().stream()
                .map(EntityDtoMapper::mappedToProductDto)
                .map(productModelAssembler::toModel)
                .collect(Collectors.toList());
    }
//productCache.getProduct(id).orElseGet(() ->
    public EntityModel<ProductDto> getProductDto(Long id) {
        ProductDto productDto = productRepository.findById(id)
                .map(EntityDtoMapper::mappedToProductDto)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productModelAssembler.toModel(productDto);
    }

    public EntityModel<ProductDto> createProductDtoEntityModel(ProductDto newProduct) {
        if (productRepository.findByName(newProduct.getName()).isEmpty()) {
            Product product = EntityDtoMapper.mappedToProductEntity(newProduct);
          //  productCache.saveProductInCache(newProduct);
            productRepository.save(product);
            ProductDto productDto = EntityDtoMapper.mappedToProductDto(product);
            return productModelAssembler.toModel(productDto);
        } else {
            Product product = productRepository.findByName(newProduct.getName()).get();
            product.setLevelStack(product.getLevelStack() + newProduct.getLevelStack());
            productRepository.save(product);
            ProductDto productDto = EntityDtoMapper.mappedToProductDto(product);
           // productCache.saveProductInCache(productDto);
            return productModelAssembler.toModel(productDto);
        }
    }

    public void deleteCustomerById(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        //productCache.deleteProductFromCache(id);
        productRepository.deleteById(id);
    }

    public EntityModel<ProductDto> updateProductAndGetModel(ProductDto newProduct, Long id) {
        newProduct.setId(id);
        productRepository.findById(id)
                .map(product -> {
                            if (newProduct.getName() != null) {
                                product.setName(newProduct.getName());
                            }
                            if (newProduct.getLevelStack() != null) {
                                product.setLevelStack(product.getLevelStack() + newProduct.getLevelStack());
                            }
                            if (newProduct.getPrice() != null) {
                                product.setPrice(newProduct.getPrice());
                            }
                            BeanUtils.copyProperties(product, newProduct);
                           // productCache.saveProductInCache(newProduct);
                            return productRepository.save(product);
                        }
                )
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productModelAssembler.toModel(newProduct);
    }
    public List<EntityModel<ProductDto>> getAllProductsModelByFilter(String name, Double minPrice, Double maxPrice, Integer minStack, Integer maxStack) {
        List<Product> byNameList;
        if (name != null) {
            byNameList = productRepository.findByNameList(name);
        } else {
            byNameList = productRepository.findAll();
        }
        return byNameList.stream()
                .filter(product -> minPrice == null || minPrice <= product.getPrice().doubleValue())
                .filter(product -> maxPrice == null || maxPrice >= product.getPrice().doubleValue())
                .filter(product -> minStack == null || minStack <= product.getLevelStack())
                .filter(product -> maxStack == null || maxStack >= product.getLevelStack())
                .map(EntityDtoMapper::mappedToProductDto)
                .map(productModelAssembler::toModel)
                .collect(Collectors.toList());
    }
}
