package com.spiashko.spotifycontrol.authcredsstore;

public interface AuthCredsStoreService {

	void loadFromStore();

	void writeToStore();

	boolean isStoreExists();

}
