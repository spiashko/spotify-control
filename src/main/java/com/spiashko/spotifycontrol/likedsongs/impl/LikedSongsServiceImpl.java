package com.spiashko.spotifycontrol.likedsongs.impl;

import com.spiashko.spotifycontrol.likedsongs.LikedSongsService;
import com.spiashko.spotifycontrol.utils.RefreshTemplate;
import com.spiashko.spotifycontrol.utils.UnpageTemplate;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.SavedTrack;

@Slf4j
@RequiredArgsConstructor
@Component
public class LikedSongsServiceImpl implements LikedSongsService {

	private final SpotifyApi spotifyApi;
	private final RefreshTemplate refreshTemplate;
	private final UnpageTemplate unpageTemplate;

	@Override
	public void likeSongsInPlaylist(String playlistId) {
		val playlistItems =
			refreshTemplate.execWithRefresh(() ->
				unpageTemplate.exec(() -> spotifyApi.getPlaylistsItems(playlistId), 100)
			);

		Collections.reverse(playlistItems);

		val counter = new Counter();
		playlistItems
			.forEach(track -> saveTracksForUser(track, counter));

		List<SavedTrack> savedTracks = refreshTemplate.execWithRefresh(() ->
			unpageTemplate.exec(() -> spotifyApi.getUsersSavedTracks(), 20)
		);

		log.info("saved tracks size: " + savedTracks.size());
	}

	@SneakyThrows
	private void saveTracksForUser(PlaylistTrack track, Counter counter) {
		log.info("track: " + ++counter.value);
		spotifyApi.saveTracksForUser(track.getTrack().getId()).build().execute();
		Thread.sleep(1000);
	}

	private static class Counter {

		public int value = 0;

	}
}
