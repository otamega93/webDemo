package com.example.web.webDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan("com.example")
public class WebDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebDemoApplication.class, args);
	}
	
	@RestController
	@RequestMapping("/persons")
	public class PersonController {
		
		@Autowired
		private ConfigurableApplicationContext configurableApplicationContext;
		
		@GetMapping("")
		public void access() {
			
			System.out.println(configurableApplicationContext.getBean("person").toString());
			System.out.println(configurableApplicationContext.getBean("personTyler").toString());
		}
	}
}
