package com.asset_management.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asset_management.app.entity.VendorEntity;
import com.asset_management.app.repository.VendorRepository;

@Service
public class VendorService {
	@Autowired
    private VendorRepository vendorRepo;
	
	public void saveVendor(VendorEntity vendor) {
		vendorRepo.save(vendor);
    }

    public List<VendorEntity> getAllVendors() {
        return vendorRepo.findAll();
    }

    public VendorEntity getVendorById(Long id) {
        return vendorRepo.findById(id);
    }
    
    public VendorEntity updateVendor(VendorEntity vendor) {
        return vendorRepo.updateVendor(vendor);
    }
    
    public void deleteVendor(Long id) {
    	vendorRepo.deleteById(id);
    }
}
