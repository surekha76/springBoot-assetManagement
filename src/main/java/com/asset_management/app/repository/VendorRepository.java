package com.asset_management.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asset_management.app.entity.VendorEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class VendorRepository {
	@Autowired
	EntityManager entityManager;

	public void save(VendorEntity vendor) {
		entityManager.persist(vendor);
	}

	public List<VendorEntity> findAll() {
		return entityManager.createQuery("Select e from VendorEntity e", VendorEntity.class).getResultList();
	}

	public VendorEntity findById(Long id) {
		return entityManager.find(VendorEntity.class, id);
	}
	
	public VendorEntity findByName(String vendorName) {
		String hql = "FROM VendorEntity v WHERE v.vendorName = :vendorName";
		TypedQuery<VendorEntity> query = entityManager.createQuery(hql, VendorEntity.class);
		query.setParameter("vendorName", vendorName);
	    try {
	    	return query.getSingleResult();
	    } catch (Exception e) {
	    	return null;
	    }
	}

	public VendorEntity updateVendor(VendorEntity vendor) {
		VendorEntity existingVendor = entityManager.find(VendorEntity.class, vendor.getVendorId());

		if (existingVendor != null) {
			existingVendor.setVendorName(vendor.getVendorName());
			existingVendor.setPhoneNo(vendor.getPhoneNo());
			existingVendor.setEmail(vendor.getEmail());
			existingVendor.setPhoneNo(vendor.getPhoneNo());
			existingVendor.setAddress(vendor.getAddress());
			existingVendor.setGstinNumber(vendor.getGstinNumber());
			return entityManager.merge(existingVendor);
		} else {
			return null;
		}
	}

	public void deleteById(Long id) {
		VendorEntity vendor = entityManager.find(VendorEntity.class, id);
		if (vendor != null) {
			entityManager.remove(vendor);
		}
	}
}
