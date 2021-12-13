package com.example.payroll.services;

import com.example.payroll.entity.Customer;
import com.example.payroll.errors.CustomerNotFoundException;
import com.example.payroll.repository.CustomerRepository;
import com.example.payroll.rest.dto.CustomerDto;
import com.example.payroll.until.CustomerModelAssembler;
import com.example.payroll.until.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomersService {
    private final CustomerRepository customerRepository;
    private final CustomerModelAssembler customerModelAssembler;

    public EntityModel<CustomerDto> updateCustomerAndGetModel(CustomerDto newCustomer, Long id) {
        newCustomer.setId(id);
        customerRepository.findById(id)
                .map(customer -> {
                    BeanUtils.copyProperties(newCustomer, customer);
//                    customerCache.saveCustomerInCache(newCustomer);
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return customerModelAssembler.toModel(newCustomer);
    }

    public List<EntityModel<CustomerDto>> getAllEntityModelCustomers() {
        return customerRepository.findAll().stream()
                .map(EntityDtoMapper::mappedToCustomerDto)
                .map(customerModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    public EntityModel<CustomerDto> getCustomerDto(Long id) {
//        CustomerDto customerDto = customerCache.getCustomer(id).orElseGet(() -> (customerRepository.findById(id)
//             .map(EntityDtoMapper::mappedToCustomerDto)
//                .orElseThrow(() -> new CustomerNotFoundException(id))));
        CustomerDto customerDto = customerRepository.findById(id)
                .map(EntityDtoMapper::mappedToCustomerDto)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return customerModelAssembler.toModel(customerDto);
    }

    public EntityModel<CustomerDto> createCustomerDtoEntityModel(CustomerDto newCustomer) {
        Customer customer = EntityDtoMapper.mappedToCustomerEntity(newCustomer);
        customerRepository.save(customer);
        CustomerDto customerDto = EntityDtoMapper.mappedToCustomerDto(customer);
//        customerCache.saveCustomerInCache(customerDto);
        return customerModelAssembler.toModel(customerDto);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.deleteById(id);
//        customerCache.deleteCustomerFromCache(id);
    }

    public List<EntityModel<CustomerDto>> getAllEntityModelCustomersByFilter(String name) {
        return customerRepository.findByNameAndAddress(name).stream()
                .map(EntityDtoMapper::mappedToCustomerDto)
                .map(customerModelAssembler::toModel)
                .collect(Collectors.toList());
    }
}
