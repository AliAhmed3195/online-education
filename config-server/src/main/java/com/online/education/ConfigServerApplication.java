package com.online.education;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.util.List;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

//	@Value("${gateway.server.swagger.permission}")
//	private Boolean swaggerPermission;

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
