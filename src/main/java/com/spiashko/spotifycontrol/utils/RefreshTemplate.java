package com.spiashko.spotifycontrol.utils;

import com.spiashko.spotifycontrol.authcredsstore.AuthCredsStoreService;
import com.spiashko.spotifycontrol.authorization.AuthorizationService;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

@RequiredArgsConstructor
@Component
public class RefreshTemplate {

	private final AuthorizationService authService;
	private final AuthCredsStoreService authCredsStoreService;

	public <T> T execWithRefresh(Supplier<T> httpCall) {
		try {
			return httpCall.get();
		} catch (Exception ex) {
			if (ex instanceof UnauthorizedException) {
				authService.refresh();
				authCredsStoreService.writeToStore();
				return httpCall.get();
			} else {
				throw ex;
			}
		}
	}
}
