package com.asset_management.app.entity;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name="asset")

public class AssetEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="assetId")
    private Long assetId;
	@Column(name="assetName")
    private String assetName;
	@Column(name="category")
    private String category;
	@Column(name="assetType")
    private String assetType;
	@Column(name="modelNo")
    private String modelNo;
	@Column(name="serialNo")
    private String serialNo;
	@Column(name="purchaseDate")
    private Date purchaseDate;
	@Column(name="warrantyDate")
    private Date warrantyDate;
	@Column(name="purchasePrice")
    private String purchasePrice;
	@Column(name="purchaseType")
    private String purchaseType;
	@Column(name = "status")
    private String status="Unassigned";
	@ManyToOne() // Many assets can belong to one vendor
    @JoinColumn(name = "vendorId")
    private VendorEntity vendor;
	@ManyToOne() // Many assets can assign to one employee
	@JoinColumn(name="employeeId")
    private EmployeeEntity employee;
	@Column(name="assignedDate")
    private Date assignedDate;
	@Column(name="assignedBy")
    private String assignedBy;
	
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Date getWarrantyDate() {
		return warrantyDate;
	}
	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public VendorEntity getVendor() {
		return vendor;
	}
	public void setVendor(VendorEntity vendor) {
		this.vendor = vendor;
	}
	public EmployeeEntity getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}
	public Date getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getAssignedBy() {
		return assignedBy;
	}
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}
}
