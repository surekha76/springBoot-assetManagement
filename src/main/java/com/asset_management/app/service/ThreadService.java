package com.asset_management.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.asset_management.app.entity.AssetEntity;
import com.asset_management.app.entity.EmployeeEntity;
import com.asset_management.app.entity.VendorEntity;
import com.asset_management.app.repository.AssetRepository;
import com.asset_management.app.repository.EmployeeRepository;
import com.asset_management.app.repository.VendorRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Service
public class ThreadService {
	@Autowired
	private EmployeeRepository employeeRepo;	
	@Autowired
	private VendorRepository vendorRepo;
	@Autowired
	private AssetRepository assetRepo;
	private boolean asset = false;
	private boolean vendor = false;
//	@Scheduled(cron = "* 1 * * * ?")
//	@Override
//	public void execute(JobExecutionContext job)throws JobExecutionException  {
//		System.out.println("Scheduler............");
//		 System.out.println("Periodic task: " + new Date());
//	}
	
	@Async
    public CompletableFuture<Void> readEmployeeData() throws IOException {
		System.out.println("Scheduler executing..........");
		return CompletableFuture.runAsync(()->{
			 System.out.println("Async thread "+Thread.currentThread().getName());
			 FileInputStream fileInput = null;
		        try {
		        	File file = new File("C:\\docs\\Asset_Management.xlsx"); 
		        	fileInput = new FileInputStream(file);
					XSSFWorkbook wb = new XSSFWorkbook(fileInput);   
					XSSFSheet sheet = wb.getSheetAt(0);  
					int rowNumber = 0;
					for (Row row : sheet) {
						if(rowNumber==0) {
							rowNumber++;
							continue;
						}
						try {
			                String firstName = row.getCell(1).getStringCellValue().toLowerCase();
			                String lastName = row.getCell(2).getStringCellValue().toLowerCase();
			                String department = row.getCell(3).getStringCellValue().toLowerCase();
			                String phoneNo = row.getCell(4).getStringCellValue(); 
			                String email = row.getCell(5).getStringCellValue().toLowerCase();
			                String status = row.getCell(6).getStringCellValue();
			                
			                EmployeeEntity employee = new EmployeeEntity();
			                employee.setFirstName(firstName);
			                employee.setLastName(lastName);
			                employee.setDepartment(department);
			                employee.setPhoneNo(phoneNo);
			                employee.setEmail(email);
			                employee.setStatus(status);
			                employeeRepo.save(employee);
						} catch (Exception e) {
							System.out.println(e.getMessage());				
						}
		            }  
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
				} catch(Exception e) { 
					System.out.println(e.getMessage());
				} finally{
					try {
						fileInput.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		});
    }
	@Async
	public CompletableFuture<Void> readVendorData() throws IOException {
		return CompletableFuture.runAsync(()-> {
			System.out.println("Async thread "+Thread.currentThread().getName());
	        FileInputStream fileInput = null;
	        try {
	        	File file = new File("C:\\docs\\Asset_Management.xlsx"); 
	        	fileInput = new FileInputStream(file);
				XSSFWorkbook wb = new XSSFWorkbook(fileInput);   
				XSSFSheet sheet = wb.getSheetAt(1);  
				int rowNumber = 0;
				for (Row row : sheet) {
					if(rowNumber==0) {
						rowNumber++;
						continue;
					}
					try {
		                String vendorName = row.getCell(1).getStringCellValue().toLowerCase();
		                String phoneNo = row.getCell(2).getStringCellValue();
		                String email = row.getCell(3).getStringCellValue().toLowerCase();
		                String GSTNo = row.getCell(4).getStringCellValue(); 
		                String address = row.getCell(5).getStringCellValue().toLowerCase();
		                
		                VendorEntity vendor = new VendorEntity();
		                vendor.setVendorName(vendorName);
		                vendor.setPhoneNo(phoneNo);
		                vendor.setEmail(email);
		                vendor.setGstinNumber(GSTNo);
		                vendor.setAddress(address);
		                vendorRepo.save(vendor);
					} catch (Exception e) {
						System.out.println(e.getMessage());				
					}
	            }  
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch(Exception e) { 
				System.out.println(e.getMessage());
			} finally {
				try {
					fileInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	@Async
	public CompletableFuture<Void> readAssetData() throws IOException {
		CompletableFuture<Void> future = readVendorData();
        future.join();
        return CompletableFuture.runAsync(()->{
        	System.out.println("Async thread "+Thread.currentThread().getName());
            FileInputStream fileInput = null;
            try {
            	File file = new File("C:\\docs\\Asset_Management.xlsx"); 
            	fileInput = new FileInputStream(file);
    			XSSFWorkbook wb = new XSSFWorkbook(fileInput);   
    			XSSFSheet sheet = wb.getSheetAt(2);  
    			int rowNumber = 0;
    			for (Row row : sheet) {
    				if(rowNumber==0) {
    					rowNumber++;
    					continue;
    				}
    				try {
    	                String assetName = row.getCell(1).getStringCellValue().toLowerCase();
    	                String assetType = row.getCell(2).getStringCellValue().toLowerCase();
    	                String category = row.getCell(3).getStringCellValue().toLowerCase();
    	                String modelNo = row.getCell(4).getStringCellValue(); 
    	                String serialNo = row.getCell(5).getStringCellValue();
    	                String purchasePrice = row.getCell(6).getStringCellValue();
    	                Date purchaseDate =  row.getCell(7).getDateCellValue();
    	                Date warrantyDate = row.getCell(8).getDateCellValue();
    	                String status = row.getCell(9).getStringCellValue(); 
    	                String vendorName = row.getCell(10).getStringCellValue().toLowerCase();
    	                String purchaseType = row.getCell(11).getStringCellValue();
    	                VendorEntity vendor = vendorRepo.findByName(vendorName);
    	                AssetEntity asset = new AssetEntity();
    	                asset.setAssetName(assetName);
    	                asset.setAssetType(assetType);
    	                asset.setCategory(category);
    	                asset.setModelNo(modelNo);
    	                asset.setSerialNo(serialNo);
    	                asset.setPurchasePrice(purchasePrice);
    	                asset.setPurchaseDate(new java.sql.Date(purchaseDate.getTime())); //typecasting util.date to sql.date
    	                asset.setWarrantyDate(new java.sql.Date(warrantyDate.getTime())); //typecasting util.date to sql.date
    	                asset.setStatus(status);
    	                asset.setVendor(vendor);
    	                asset.setPurchaseType(purchaseType);
    	                assetRepo.save(asset);
    				} catch (Exception e) {
    					System.out.println(e.getMessage());				
    				}
                }  
    		} catch (FileNotFoundException e) {
    			System.out.println(e.getMessage());
    		} catch(Exception e) { 
    			System.out.println(e.getMessage());
    		} finally{
    			try {
					fileInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
        });
	}
	@Async
	public CompletableFuture<Void> readAssignedData() throws IOException{
		CompletableFuture<Void> future = readAssetData();
        future.join();
        return CompletableFuture.runAsync(()->{
        	System.out.println("Async thread "+Thread.currentThread().getName());
            FileInputStream fileInput = null;
            try {
            	File file = new File("C:\\docs\\Asset_Management.xlsx"); 
            	fileInput = new FileInputStream(file);
    			XSSFWorkbook wb = new XSSFWorkbook(fileInput);   
    			XSSFSheet sheet = wb.getSheetAt(3);  
    			int rowNumber = 0;
    			for (Row row : sheet) {
    				if(rowNumber==0) {
    					rowNumber++;
    					continue;
    				}
    				try {
    	                String assetName = row.getCell(0).getStringCellValue().toLowerCase();
    	                String assignedTo = row.getCell(1).getStringCellValue().toLowerCase();
    	                String department = row.getCell(2).getStringCellValue().toLowerCase();
    	                String assignedBy = row.getCell(3).getStringCellValue().toLowerCase(); 
    	                Date assignedDate = row.getCell(4).getDateCellValue();
    	                String email = row.getCell(5).getStringCellValue().toLowerCase(); 
    	                EmployeeEntity employee = employeeRepo.findByName(assignedTo, department, email);
    	                AssetEntity asset = assetRepo.findByName(assetName);
    	                asset.setEmployee(employee);
    	                asset.setAssignedBy(assignedBy);
    	                asset.setAssignedDate(new java.sql.Date(assignedDate.getTime()));
    	                System.out.println(employee);
    	                assetRepo.assignAsset(asset);
    				} catch (Exception e) {
    					System.out.println(e.getMessage());				
    				}
                }  
    		} catch (FileNotFoundException e) {
    			System.out.println(e.getMessage());
    		} catch(Exception e) { 
    			System.out.println(e.getMessage());
    		} finally{
    			try {
					fileInput.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
    		}
        });
	}
//	EDIT .......................................................................................
	@Async
	public synchronized void vendor() {
		while (!vendor) {
			System.out.println("Async thread "+Thread.currentThread().getName());
	        FileInputStream fileInput = null;
	        try {
	        	File file = new File("C:\\docs\\Asset_Management.xlsx"); 
	        	fileInput = new FileInputStream(file);
				XSSFWorkbook wb = new XSSFWorkbook(fileInput);   
				XSSFSheet sheet = wb.getSheetAt(1);  
				int rowNumber = 0;
				for (Row row : sheet) {
					if(rowNumber==0) {
						rowNumber++;
						continue;
					}
					try {
		                String vendorName = row.getCell(1).getStringCellValue().toLowerCase();
		                String phoneNo = row.getCell(2).getStringCellValue();
		                String email = row.getCell(3).getStringCellValue().toLowerCase();
		                String GSTNo = row.getCell(4).getStringCellValue(); 
		                String address = row.getCell(5).getStringCellValue().toLowerCase();
		                
		                VendorEntity vendor = new VendorEntity();
		                vendor.setVendorName(vendorName);
		                vendor.setPhoneNo(phoneNo);
		                vendor.setEmail(email);
		                vendor.setGstinNumber(GSTNo);
		                vendor.setAddress(address);
		                vendorRepo.save(vendor);
					} catch (Exception e) {
						System.out.println(e.getMessage());				
					}
	            }  
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch(Exception e) { 
				System.out.println(e.getMessage());
			} finally {
				vendor = true;
				notify();
				try {
					fileInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Async
	public synchronized void asset() throws InterruptedException {
		while(!vendor) {
			System.out.println("waiting for vendor...........");
			wait();
		}
		System.out.println("Async thread "+Thread.currentThread().getName());
        FileInputStream fileInput = null;
        try {
        	File file = new File("C:\\docs\\Asset_Management.xlsx"); 
        	fileInput = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fileInput);   
			XSSFSheet sheet = wb.getSheetAt(2);  
			int rowNumber = 0;
			for (Row row : sheet) {
				if(rowNumber==0) {
					rowNumber++;
					continue;
				}
				try {
	                String assetName = row.getCell(1).getStringCellValue().toLowerCase();
	                String assetType = row.getCell(2).getStringCellValue().toLowerCase();
	                String category = row.getCell(3).getStringCellValue().toLowerCase();
	                String modelNo = row.getCell(4).getStringCellValue(); 
	                String serialNo = row.getCell(5).getStringCellValue();
	                String purchasePrice = row.getCell(6).getStringCellValue();
	                Date purchaseDate =  row.getCell(7).getDateCellValue();
	                Date warrantyDate = row.getCell(8).getDateCellValue();
	                String status = row.getCell(9).getStringCellValue(); 
	                String vendorName = row.getCell(10).getStringCellValue().toLowerCase();
	                String purchaseType = row.getCell(11).getStringCellValue();
	                VendorEntity vendor = vendorRepo.findByName(vendorName);
	                AssetEntity asset = new AssetEntity();
	                asset.setAssetName(assetName);
	                asset.setAssetType(assetType);
	                asset.setCategory(category);
	                asset.setModelNo(modelNo);
	                asset.setSerialNo(serialNo);
	                asset.setPurchasePrice(purchasePrice);
	                asset.setPurchaseDate(new java.sql.Date(purchaseDate.getTime())); //typecasting util.date to sql.date
	                asset.setWarrantyDate(new java.sql.Date(warrantyDate.getTime())); //typecasting util.date to sql.date
	                asset.setStatus(status);
	                asset.setVendor(vendor);
	                asset.setPurchaseType(purchaseType);
	                assetRepo.save(asset);
				} catch (Exception e) {
					System.out.println(e.getMessage());				
				}
            }  
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) { 
			System.out.println(e.getMessage());
		} finally{
			asset = true;
			notify();
			try {
				fileInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Async
	public synchronized void assign() throws InterruptedException {
		while(!asset) {
			System.out.println("Waithing for asset.................");
			wait();
		}
		System.out.println("Async thread "+Thread.currentThread().getName());
        FileInputStream fileInput = null;
        try {
        	File file = new File("C:\\docs\\Asset_Management.xlsx"); 
        	fileInput = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fileInput);   
			XSSFSheet sheet = wb.getSheetAt(3);  
			int rowNumber = 0;
			for (Row row : sheet) {
				if(rowNumber==0) {
					rowNumber++;
					continue;
				}
				try {
	                String assetName = row.getCell(0).getStringCellValue().toLowerCase();
	                String assignedTo = row.getCell(1).getStringCellValue().toLowerCase();
	                String department = row.getCell(2).getStringCellValue().toLowerCase();
	                String assignedBy = row.getCell(3).getStringCellValue().toLowerCase(); 
	                Date assignedDate = row.getCell(4).getDateCellValue();
	                String email = row.getCell(5).getStringCellValue().toLowerCase(); 
	                EmployeeEntity employee = employeeRepo.findByName(assignedTo, department, email);
	                AssetEntity asset = assetRepo.findByName(assetName);
	                asset.setEmployee(employee);
	                asset.setAssignedBy(assignedBy);
	                asset.setAssignedDate(new java.sql.Date(assignedDate.getTime()));
	                System.out.println(employee);
	                assetRepo.assignAsset(asset);
				} catch (Exception e) {
					System.out.println(e.getMessage());				
				}
            }  
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) { 
			System.out.println(e.getMessage());
		} finally{
			try {
				fileInput.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
