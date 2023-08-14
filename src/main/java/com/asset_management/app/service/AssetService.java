package com.asset_management.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asset_management.app.entity.AssetEntity;
import com.asset_management.app.repository.AssetRepository;

@Service
public class AssetService {
	@Autowired
    private AssetRepository assetRepo;
	
	public void saveAsset(AssetEntity asset) {	
		assetRepo.save(asset);
    }

    public List<AssetEntity> getAllAsset() {
        return assetRepo.findAll();
    }

    public AssetEntity getAssetById(Long id) {
        return assetRepo.findById(id);
    }
    
    public AssetEntity updateAsset(AssetEntity asset) {
        return assetRepo.updateAsset(asset);
    }
    
    public String deleteAsset(Long id) {
    	return assetRepo.deleteById(id);
    }
    
    public AssetEntity assignAsset(AssetEntity asset) {
    	return assetRepo.assignAsset(asset);
    }
    public AssetEntity unassignAsset(Long id) {
    	return assetRepo.unassignAsset(id);
    }
}
