package com.asset_management.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.asset_management.app.*"})
@ComponentScan(basePackages = {"com.asset_management.app", "com.asset_management.app.controller", "com.asset_management.app.entity", "com.asset_management.app.service"})
public class AssetManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetManagementApplication.class, args);
	}

}
