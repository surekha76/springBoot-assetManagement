package com.asset_management.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asset_management.app.entity.AssetEntity;
import com.asset_management.app.entity.VendorEntity;
import com.asset_management.app.entity.EmployeeEntity;
import com.asset_management.app.service.AssetService;
import com.asset_management.app.service.VendorService;
import com.asset_management.app.service.EmployeeService;

@RestController
public class AssetController {
	@Autowired
	private AssetService assetService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/saveAsset")
	@ResponseBody
	public String addAsset(@RequestBody AssetEntity asset, @RequestParam Long vendorId) {
		try {
			VendorEntity vendor = vendorService.getVendorById(vendorId);
			if (vendor != null) {
				asset.setVendor(vendor); // Set the Vendor entity in the Asset entity
				assetService.saveAsset(asset); // Implement the saveAsset method in your AssetService
				String response = "Asset Inserted Successfuly";
				return response;
			}else {
				String response = "Vendor details was null";
				return response;
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/getAllAsset")
	@ResponseBody
	public List<AssetEntity> getAllAsset() {
		return assetService.getAllAsset();
	}

	@GetMapping("/getAsset")
	@ResponseBody
	public AssetEntity getAsset(@RequestParam Long id) {
		return assetService.getAssetById(id);
	}

	@PostMapping("/updateAsset")
	@ResponseBody
	public String updateAsset(@RequestBody AssetEntity asset,@RequestParam Long vendorId) {
		try {
			VendorEntity vendor = vendorService.getVendorById(vendorId);
			if (vendor != null) {
				asset.setVendor(vendor); 
				assetService.updateAsset(asset); 
				String response = "Asset Updated Successfuly";
				return response;
			}else {
				String response = "Vendor details was null";
				return response;
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@PostMapping("/deleteAsset")
	@ResponseBody
	public String deleteAsset(@RequestParam Long id) {
		try {
			return assetService.deleteAsset(id);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@PostMapping("/AssignAsset")
	@ResponseBody
	public String AssignAsset(@RequestBody AssetEntity asset, @RequestParam Long employeeId) {
		try {
			EmployeeEntity employee = employeeService.getEmployeeById(employeeId);
			if (employee != null) {
				asset.setEmployee(employee); 
				assetService.assignAsset(asset); 
				String response = "Asset Assigned Successfuly";
				return response;
			}else {
				String response = "Vendor details was null";
				return response;
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@PostMapping("/unassignAsset")
	@ResponseBody
	public String unassignAsset(@RequestParam Long id) {
		try {
			assetService.unassignAsset(id);
			String response = "Asset Unassigned Successfully";
			return response;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
