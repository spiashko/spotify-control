package com.spiashko.spotifycontrol.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeans {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
