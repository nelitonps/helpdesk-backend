package com.npsouza.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.npsouza.helpdesk.services.DBService;

import jakarta.annotation.PostConstruct;

@Configuration
@Profile("test") //Aponta para o perfil do application.properties
public class TestConfig {
	
	@Autowired
	private DBService dbService;

    //Para permitir que o método suba de forma automática
	@PostConstruct  //É usado no lugar do @Bean
    public void instanciaDBtest() {
		this.dbService.instanciaDB();
	}
	
}
