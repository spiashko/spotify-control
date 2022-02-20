package com.spiashko.spotifycontrol.authorization;

public interface AuthorizationService {

	void performAuth();

	void refresh();

}
