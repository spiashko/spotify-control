package com.spiashko.spotifycontrol.authcredsstore.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spiashko.spotifycontrol.authcredsstore.AuthCredsStoreService;
import com.spiashko.spotifycontrol.config.AuthCredsStoreProps;
import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;

@RequiredArgsConstructor
@Component
public class AuthCredsStoreServiceImpl implements AuthCredsStoreService {

	private final ObjectMapper objectMapper;
	private final SpotifyApi spotifyApi;
	private final AuthCredsStoreProps props;

	@SneakyThrows
	@Override
	public void loadFromStore() {
		File backup = new File(props.getFilename());
		val restoredCreds = objectMapper.readValue(backup, CredsModel.class);
		spotifyApi.setAccessToken(restoredCreds.getAccessToken());
		spotifyApi.setRefreshToken(restoredCreds.getRefreshToken());
	}

	@SneakyThrows
	@Override
	public void writeToStore() {
		File backup = new File(props.getFilename());
		CredsModel model = CredsModel.builder()
			.accessToken(spotifyApi.getAccessToken())
			.refreshToken(spotifyApi.getRefreshToken())
			.build();
		objectMapper.writeValue(backup, model);
	}

	@Override
	public boolean isStoreExists() {
		File backup = new File(props.getFilename());
		return backup.exists();
	}

}
