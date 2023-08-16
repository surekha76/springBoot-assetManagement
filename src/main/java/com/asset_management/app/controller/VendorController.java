package com.asset_management.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asset_management.app.entity.VendorEntity;
import com.asset_management.app.service.VendorService;

@RestController
public class VendorController {
	@Autowired
    private VendorService vendorService;
		
	    @PostMapping("/saveVendor")
	    @ResponseBody
	    public String addVendor(@RequestBody VendorEntity vendor) {
	    	try {
	    		vendorService.saveVendor(vendor);
	    		 String response = "Vendor Inserted Successfuly";
	             return response;
	    	}catch(DataIntegrityViolationException e) {
	    		 if (e.getMessage() != null && e.getMessage().contains("duplicate key value")) {
	    		        return "Vendor already exist";
	    		    } else {
	    		        return e.getMessage();
	    		    }
	    	}catch(Exception e) {
	    		return e.getMessage();
	    	}
	    }
	    
	    @GetMapping("/getAllVendor")
	    @ResponseBody
	    public  List<VendorEntity> getAllVendors() {
	    	return vendorService.getAllVendors();
	    }
	    
	    @GetMapping("/getVendor")
	    @ResponseBody
	    public VendorEntity getVendor(@RequestParam Long id) {
	    	return vendorService.getVendorById(id);
	    }

	    @PostMapping("/updateVendor")
	    @ResponseBody
	    public String updateVendor(@RequestBody VendorEntity vendor) {
	    	try {
	    		vendorService.updateVendor(vendor);
	    		String response = "Vendor Updated Successfuly";
	            return response;
	    	}catch(Exception e) {
	    		return e.getMessage();
	    	}
	    }
    
	    @PostMapping("/deleteVendor")
	    @ResponseBody
	    public String deleteVendor(@RequestParam Long id) {
	    	try {
	    		vendorService.deleteVendor(id);
	    		String response = "Vendor Deleted Successfuly";
	            return response;
	    	}catch(Exception e) {
	    		return e.getMessage();
	    	}
	    }
}
