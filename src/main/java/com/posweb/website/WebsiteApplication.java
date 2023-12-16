package com.posweb.website;

import com.posweb.website.Service.DatabaseService;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class WebsiteApplication implements ApplicationRunner {

	private final DatabaseService databaseService;

	@Autowired
	public WebsiteApplication(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		databaseService.creatUserIfNotExists();
	}
}