package com.spiashko.spotifycontrol.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("auth-http-server")
public class AuthHttpServerProps {

	private Integer port;
	private String host;
	private String requestMapping; // /callback
	private Integer timeout;

}
