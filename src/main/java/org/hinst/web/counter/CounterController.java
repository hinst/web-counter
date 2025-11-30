package org.hinst.web.counter;

import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CounterController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final UrlEntryRepository urlEntryRepository;
	private final RiddleManager riddleManager;

	@PostMapping(value = "/ping")
	public String ping(
		@RequestHeader Map<String, String> headers,
		@RequestParam @NonNull String url,
		@RequestParam long riddleId,
		@RequestBody int[] riddleAnswer
	) {
		if (StringUtils.isBlank(url))
			throw new IllegalArgumentException("URL is required");
		riddleManager.verifyAnswer(riddleId, riddleAnswer);
		increaseCount(url);
		logger.info("Pinged URL: {} with headers: {}", url, headers);
		return "ok";
	}

	private void increaseCount(@NonNull String url) {
		synchronized(UrlEntryRepository.locker) {
			var entry = urlEntryRepository.findById(url).orElseGet(() -> {
				var newEntry = new UrlEntry();
				newEntry.setUrl(url);
				newEntry.setCount(0L);
				return newEntry;
			});
			entry.setCount(entry.getCount() + 1);
			urlEntryRepository.save(entry);
		}
	}
}
