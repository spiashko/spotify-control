package com.spiashko.spotifycontrol.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("spotify.client")
public class SpotifyClientProps {

	private String id;
	private String secret;
	private String redirectUri;

}
