package com.spiashko.spotifycontrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

@Configuration
public class SpotifyApiConfig {

	@Bean
	public SpotifyApi spotifyApi(SpotifyClientProps props) {
		return new SpotifyApi.Builder()
			.setClientId(props.getId())
			.setClientSecret(props.getSecret())
			.setRedirectUri(SpotifyHttpManager.makeUri(props.getRedirectUri()))
			.build();
	}

}
