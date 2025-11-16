package org.hinst.web.counter;

import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CounterController {
	private Logger logger = LoggerFactory.getLogger(CounterController.class);
	private final UrlEntryRepository urlEntryRepository;

	@PostMapping(value = "/ping")
	public String ping(
		@RequestHeader Map<String, String> headers,
		@RequestParam @NonNull String url
	) {
		Objects.requireNonNull(url, "URL is required");
		if (StringUtils.isBlank(url))
			throw new IllegalArgumentException("URL should be filled in");
		logger.info("Ping received for URL: {} with headers: {}", url, headers);
		increaseCount(url);
		return "ok";
	}

	private void increaseCount(String url) {
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
