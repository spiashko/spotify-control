package com.spiashko.spotifycontrol;

import com.spiashko.spotifycontrol.authcredsstore.AuthCredsStoreService;
import com.spiashko.spotifycontrol.authorization.AuthorizationService;
import com.spiashko.spotifycontrol.likedsongs.LikedSongsService;
import com.spiashko.spotifycontrol.utils.RefreshTemplate;
import com.spiashko.spotifycontrol.utils.UnpageTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;

@RequiredArgsConstructor
@Slf4j
@Component
public class AppRunner implements CommandLineRunner {

	private final AuthorizationService authorizationService;
	private final AuthCredsStoreService authCredsStoreService;
	private final RefreshTemplate refreshTemplate;
	private final UnpageTemplate unpageTemplate;
	private final SpotifyApi spotifyApi;

	private final LikedSongsService likedSongsService;

	public void run(String... args) {
		log.info("AppRunner started");

		if (!authCredsStoreService.isStoreExists()) {
			authorizationService.performAuth();
			authCredsStoreService.writeToStore();
		} else {
			authCredsStoreService.loadFromStore();
		}
		val playlists =
			refreshTemplate.execWithRefresh(() ->
				unpageTemplate.exec(spotifyApi::getListOfCurrentUsersPlaylists, 20)
			);

		log.info("my playlists: {}", playlists);

		log.info("AppRunner finished");
	}

}
