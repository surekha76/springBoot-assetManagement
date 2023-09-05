package com.asset_management.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asset_management.app.entity.EmployeeEntity;
import com.asset_management.app.entity.VendorEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class EmployeeRepository {
	@Autowired
	EntityManager entityManager;
	public void save(EmployeeEntity employee) {
		entityManager.persist(employee);
	}

	public List<EmployeeEntity> findAll() {
		return entityManager.createQuery("Select e from EmployeeEntity e Order By e.employeeId",EmployeeEntity.class).getResultList();
		
	}

	public EmployeeEntity findById(Long id) {
		
		 return entityManager.find(EmployeeEntity.class, id);
	}
	
	public EmployeeEntity findByName(String employeeFirstName, String department, String email) {
		System.out.println(employeeFirstName+" "+department+" "+email);
		String hql = "FROM EmployeeEntity e WHERE e.employeeFirstName = :employeeFirstName AND e.department = :department AND e.email = :email";
		TypedQuery<EmployeeEntity> query = entityManager.createQuery(hql, EmployeeEntity.class);
		query.setParameter("employeeFirstName", employeeFirstName);
		query.setParameter("department", department);
		query.setParameter("email", email);
//		System.out.println(query.unwrap(org.hibernate.query.Query.class).getQueryString());
	    try {
	    	return query.getSingleResult();
	    } catch (Exception e) {
	    	return null;
	    }
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
