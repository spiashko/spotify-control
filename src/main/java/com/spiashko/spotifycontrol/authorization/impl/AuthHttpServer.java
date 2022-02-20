package com.spiashko.spotifycontrol.authorization.impl;

import com.spiashko.spotifycontrol.config.AuthHttpServerProps;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
final class AuthHttpServer {

	private final AuthHttpServerProps props;

	@SneakyThrows
	public String startAndWaitUntilCodeReceived() {
		val result = new Wrapper();

		var latch = new CountDownLatch(1);
		var server = HttpServer.create(new InetSocketAddress(props.getHost(), props.getPort()), 0);
		server.createContext(props.getRequestMapping(), exchange -> {
			val code = exchange.getRequestURI().toString().split("=")[1];
			result.setValue(code);

			val response = "Success! Authentication completed. You can close web browser and return to the terminal window.";

			exchange.sendResponseHeaders(200, response.length());
			exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));

			latch.countDown();
		});
		server.start();
		log.info("Waiting for redirect URI...");
		//noinspection ResultOfMethodCallIgnored
		latch.await(props.getTimeout(), TimeUnit.SECONDS);
		server.stop(0);

		return result.getValue();
	}

	@Getter
	@Setter
	private static final class Wrapper {

		private String value;
	}
}
