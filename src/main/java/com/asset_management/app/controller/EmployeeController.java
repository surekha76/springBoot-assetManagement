package com.asset_management.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asset_management.app.entity.EmployeeEntity;
import com.asset_management.app.service.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
    private EmployeeService employeeService;
		
	    @PostMapping("/saveEmployee")
	    @ResponseBody
	    public String addEmployee(@RequestBody EmployeeEntity employee) {
	    	try {
	    		employeeService.saveEmployee(employee);
	    		 String response = "Employee Inserted Successfuly";
	             return response;
	    	}catch(Exception e) {
	    		return e.getMessage();
	    	}
	    }
	    
	    @GetMapping("/getAllEmployee")
	    @ResponseBody
	    public  List<EmployeeEntity> getAllEmployee() {
	    	return employeeService.getAllEmployees();
	    }
	    
	    @GetMapping("/getEmployee")
	    @ResponseBody
	    public EmployeeEntity getEmployee(@RequestParam Long id) {
	    	return employeeService.getEmployeeById(id);
	    }

	    @PostMapping("/updateEmployee")
	    @ResponseBody
	    public String updateEmployee(@RequestBody EmployeeEntity employee) {
	    	try {
	    		employeeService.updateEmployee(employee);
	    		String response = "Employee Updated Successfuly";
	            return response;
	    	}catch(Exception e) {
	    		return e.getMessage();
	    	}
	    }
    
	    @PostMapping("/deleteEmployee")
	    @ResponseBody
	    public String deleteEmployee(@RequestParam Long id) {
	    	try {
	    		employeeService.deleteEmployee(id);
	    		String response = "Employee Deleted Successfuly";
	            return response;
	    	}catch(Exception e) {
	    		return e.getMessage();
	    	}
	    }
}
