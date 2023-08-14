package com.asset_management.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name="employee")
public class EmployeeEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="employeeId")
    private Long employeeId;
	@Column(name="employeeFirstName")
    private String employeeFirstName;
	@Column(name="employeeLastName")
    private String employeeLastName;
	@Column(name="department")
    private String department;
	@Column(name="phoneNo")
    private String phoneNo;
	@Column(name="email")
    private String email;
	@Column(name="status")
    private String status;
	public Long getId() {
		return employeeId;
	}
	public void setId(Long id) {
		this.employeeId = id;
	}
	public String getFirstName() {
		return employeeFirstName;
	}
	public void setFirstName(String firstName) {
		this.employeeFirstName = firstName;
	}
	public String getLastName() {
		return employeeLastName;
	}
	public void setLastName(String lastName) {
		this.employeeLastName = lastName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
