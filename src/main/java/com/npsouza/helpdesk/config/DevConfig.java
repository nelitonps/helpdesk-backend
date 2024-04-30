package com.npsouza.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.npsouza.helpdesk.services.DBService;

import jakarta.annotation.PostConstruct;

@Configuration
@Profile("dev") //Aponta para o perfil do application.properties
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String valor;

    //Para permitir que o método suba de forma automática
	@PostConstruct  //É usado no lugar do @Bean
    public boolean instanciaDBtest() {
		if(valor.equals("create")) {
			this.dbService.instanciaDB();
		}
		return false;
	}
	
}
