package com.tct.tctcampaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tct")
public class TctcampaignApplication {

	public static void main(String[] args) {
		SpringApplication.run(TctcampaignApplication.class, args);
	}

}
