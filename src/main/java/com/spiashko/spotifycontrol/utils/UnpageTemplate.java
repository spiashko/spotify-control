package com.spiashko.spotifycontrol.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.AbstractDataPagingRequest;

@RequiredArgsConstructor
@Component
public class UnpageTemplate {

	@SneakyThrows
	public <T, B extends AbstractDataPagingRequest.Builder<T, B>> List<T> exec(Supplier<B> initialBuilder, int limit) {
		val resultList = new ArrayList<T>();

		int offset = 0;
		Paging<T> page;

		do {
			val req = initialBuilder.get()
				.limit(limit)
				.offset(offset)
				.build();
			page = req.execute();

			resultList.addAll(Arrays.asList(page.getItems()));
			offset += limit;
		} while (page.getNext() != null);

		return resultList;
	}

}
