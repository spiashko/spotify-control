package com.spiashko.spotifycontrol.authorization.impl;

import com.spiashko.spotifycontrol.authorization.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationServiceImpl implements AuthorizationService {

	private final SpotifyApi spotifyApi;
	private final AuthHttpServer authHttpServer;

	@SneakyThrows
	@Override
	public void performAuth() {
		val authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
			.scope("user-read-private,playlist-read-private,user-library-read,user-library-modify")
			.show_dialog(true)
			.build();

		val uri = authorizationCodeUriRequest.execute();

		log.info("URI: " + uri);

		String command = "open " + uri;
		Runtime.getRuntime().exec(command);

		String code = authHttpServer.startAndWaitUntilCodeReceived();

		val authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
		val authorizationCodeCredentials = authorizationCodeRequest.execute();

		// Set access and refresh token for further "spotifyApi" object usage
		spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
		spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
	}

	@SneakyThrows
	@Override
	public void refresh() {
		val authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
			.build();

		val authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

		// Set access and refresh token for further "spotifyApi" object usage
		spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
	}
}
