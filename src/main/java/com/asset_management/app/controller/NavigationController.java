package com.asset_management.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.asset_management.app.service.ThreadService;

@RestController
public class NavigationController {
	@Autowired
	private ThreadService threadService;
	@RequestMapping(value = "/")
	public ModelAndView hello() {
		try {
			threadService.readEmployeeData();
//			threadService.readVendorData();
//			threadService.readAssetData();
//			threadService.readAssignedData();
			threadService.asset();
			threadService.vendor();
			threadService.assign();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	    return new ModelAndView("index");
	 }
	@GetMapping("/employee")
    public ModelAndView showEmployeePage() {
	    return new ModelAndView("employee");
    }
	@GetMapping("/vendor")
    public ModelAndView showVendorPage() {
	    return new ModelAndView("vendor");
    }
	@GetMapping("/asset")
    public ModelAndView showAssetPage() {
	    return new ModelAndView("asset");
    }
	@GetMapping("/assignAsset")
    public ModelAndView showAssignAssetPage() {
	    return new ModelAndView("assignAsset");
    }
}
