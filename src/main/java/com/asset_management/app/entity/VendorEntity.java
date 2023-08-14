package com.asset_management.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name="vendor")
public class VendorEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vendor_id")
	private Long vendorId;
	@Column(name="vendor_name")
	private String vendorName;
	@Column(name="vendor_phoneNo")
	private String phoneNo;
	@Column(name="vendor_email")
	private String email;
	@Column(name="vendor_address")
	private String address;
	@Column(name="vendor_gstNumber")
	private String gstinNumber;
	
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendord(Long vendor_id) {
		this.vendorId = vendor_id;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGstinNumber() {
		return gstinNumber;
	}
	public void setGstinNumber(String gstinNumber) {
		this.gstinNumber = gstinNumber;
	}
}
