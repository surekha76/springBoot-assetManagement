package com.asset_management.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class NavigationController {
	@RequestMapping(value = "/")
	public ModelAndView hello() {
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
