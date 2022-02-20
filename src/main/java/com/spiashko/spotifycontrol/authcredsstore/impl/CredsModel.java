package com.spiashko.spotifycontrol.authcredsstore.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CredsModel {

	private String accessToken;
	private String refreshToken;

}
