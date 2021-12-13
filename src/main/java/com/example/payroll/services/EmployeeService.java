package com.example.payroll.services;

import com.example.payroll.entity.Employee;
import com.example.payroll.errors.EmployeeNotFoundException;
import com.example.payroll.repository.EmployeeCache;
import com.example.payroll.repository.EmployeeRepository;
import com.example.payroll.rest.dto.EmployeeDto;
import com.example.payroll.until.EmployeeModelAssembler;
import com.example.payroll.until.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeModelAssembler assembler;
    private final EmployeeCache employeeCache;

    public EntityModel<EmployeeDto> getUpdatedEmployee(EmployeeDto newEmployee, Long id) {
        newEmployee.setId(id);
        employeeRepository.findById(id)
                .map(employee -> {
                    BeanUtils.copyProperties(newEmployee, employee);
                    employeeCache.saveEmployeeInCache(newEmployee);
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(newEmployee);
    }

    public List<EntityModel<EmployeeDto>> getAllEntityModelsEmployees() {
        return employeeRepository.findAll().stream()
                .map(EntityDtoMapper::mappedToEmployeeDto)
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    public EntityModel<EmployeeDto> createEmployeeDtoEntityModel(EmployeeDto newEmployee) {
        Employee employee = EntityDtoMapper.mappedToEmployeeEntity(newEmployee);
        employeeRepository.save(employee);
        EmployeeDto employeeDto = EntityDtoMapper.mappedToEmployeeDto(employee);
        employeeCache.saveEmployeeInCache(employeeDto);
        return assembler.toModel(employeeDto);
    }


    public EntityModel<EmployeeDto> getEntityModel(Long id) {
        EmployeeDto employeeDto = employeeCache.getEmployee(id).orElseGet(() ->employeeRepository.findById(id)
                .map(EntityDtoMapper::mappedToEmployeeDto)
                .orElseThrow(() -> new EmployeeNotFoundException(id)));
        return assembler.toModel(employeeDto);
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeCache.deleteEmployeeFromCache(id);
        employeeRepository.deleteById(id);
    }

    public List<EntityModel<EmployeeDto>> getEntityModelsEmployeesByFilters(String name, Double salaryMax, Double salaryMin) {
        List<Employee> byNameAndRole;
        if (name != null) {
            byNameAndRole = employeeRepository.findByNameAndRole(name);
        } else {
            byNameAndRole = employeeRepository.findAll();
        }
        return byNameAndRole.stream()
                .filter(employee -> salaryMin == null || salaryMax <= employee.getSalary())
                .filter(employee -> salaryMax == null || salaryMax >= employee.getSalary())
                .map(EntityDtoMapper::mappedToEmployeeDto)
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }
}
