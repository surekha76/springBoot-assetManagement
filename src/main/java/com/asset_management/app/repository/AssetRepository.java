package com.asset_management.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asset_management.app.entity.AssetEntity;

import jakarta.persistence.EntityManager;

@Repository
@Transactional
public class AssetRepository {
	@Autowired
	EntityManager entityManager;
	public void save(AssetEntity asset) {
		entityManager.persist(asset);
	}

	public List<AssetEntity> findAll() {
		return entityManager.createQuery("Select e from AssetEntity e",AssetEntity.class).getResultList();
		
	}

	public AssetEntity findById(Long id) {
		 return entityManager.find(AssetEntity.class, id);
	}
	
	public AssetEntity updateAsset(AssetEntity asset) {
		AssetEntity existingAsset = entityManager.find(AssetEntity.class, asset.getAssetId());
        if (existingAsset != null) {
        	existingAsset.setAssetName(asset.getAssetName());
        	existingAsset.setCategory(asset.getCategory());
        	existingAsset.setAssetType(asset.getAssetType());
        	existingAsset.setModelNo(asset.getModelNo());
        	existingAsset.setSerialNo(asset.getSerialNo());
        	existingAsset.setPurchaseDate(asset.getPurchaseDate());
        	existingAsset.setPurchasePrice(asset.getPurchasePrice());
        	existingAsset.setWarrantyDate(asset.getWarrantyDate());
        	existingAsset.setPurchaseType(asset.getPurchaseType());
        	//existingAsset.setStatus(asset.getStatus());
        	existingAsset.setVendor(asset.getVendor());
            return entityManager.merge(existingAsset);
        }else {
        	return null;
        }
	}
	
	public String deleteById(Long id) {
		AssetEntity asset = entityManager.find(AssetEntity.class, id);
		String status = asset.getStatus();
	    if ("Assigned".equals(status)) {
	       	return "Can't delete assigned asset.";
	    }else {
	    	entityManager.remove(asset);
	    	return "Asset Deleted Successfully";
	    }
	}
	public AssetEntity assignAsset(AssetEntity asset) {
		AssetEntity existingAsset = entityManager.find(AssetEntity.class, asset.getAssetId());
        if (existingAsset != null) {
        	existingAsset.setEmployee(asset.getEmployee());
        	existingAsset.setAssignedDate(asset.getAssignedDate());
        	existingAsset.setAssignedBy(asset.getAssignedBy());
        	existingAsset.setStatus("Assigned");
            return entityManager.merge(existingAsset);
        }else {
        	return null;
        }
	}
	public AssetEntity unassignAsset(Long id) {
		AssetEntity existingAsset = entityManager.find(AssetEntity.class, id);
		//AssetEntity existingAsset = entityManager.find(AssetEntity.class, asset.getAssetId());
        if (existingAsset != null) {
        	existingAsset.setEmployee(null);
        	existingAsset.setAssignedDate(null);
        	existingAsset.setAssignedBy(null);
        	existingAsset.setStatus("Unassigned");
            return entityManager.merge(existingAsset);
        }else {
        	return null;
        }
	}
}
