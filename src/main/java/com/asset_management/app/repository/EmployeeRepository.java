package com.asset_management.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asset_management.app.entity.EmployeeEntity;

import jakarta.persistence.EntityManager;

@Repository
@Transactional
public class EmployeeRepository {
	@Autowired
	EntityManager entityManager;
	public void save(EmployeeEntity employee) {
		entityManager.persist(employee);
	}

	public List<EmployeeEntity> findAll() {
		return entityManager.createQuery("Select e from EmployeeEntity e",EmployeeEntity.class).getResultList();
		
	}

	public EmployeeEntity findById(Long id) {
		 return entityManager.find(EmployeeEntity.class, id);
	}
	
	public EmployeeEntity updateEmployee(EmployeeEntity employee) {
		EmployeeEntity existingEmployee = entityManager.find(EmployeeEntity.class, employee.getId());
		
        if (existingEmployee != null) {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setPhoneNo(employee.getPhoneNo());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setStatus(employee.getStatus());
            return entityManager.merge(existingEmployee);
        }else {
        	return null;
        }
	}
	
	public void deleteById(Long id) {
		EmployeeEntity employee = entityManager.find(EmployeeEntity.class, id);
	        if (employee != null) {
	            entityManager.remove(employee);
	        }
	}
}
