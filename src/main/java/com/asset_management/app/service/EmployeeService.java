package com.asset_management.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.asset_management.app.entity.EmployeeEntity;
import com.asset_management.app.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
    private EmployeeRepository employeeRepo;
	
	public void saveEmployee(EmployeeEntity employee) {
		employeeRepo.save(employee);
    }

    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public EmployeeEntity getEmployeeById(Long id) {
        return employeeRepo.findById(id);
    }
    
    public EmployeeEntity updateEmployee(EmployeeEntity employee) {
        return employeeRepo.updateEmployee(employee);
    }
    
    public void deleteEmployee(Long id) {
    	employeeRepo.deleteById(id);
    }
}
