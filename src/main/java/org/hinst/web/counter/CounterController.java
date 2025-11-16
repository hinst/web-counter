package org.hinst.web.counter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CounterController {
	private final UrlEntryRepository urlEntryRepository;

	@PostMapping(value = "/ping")
	public String ping(@RequestParam @NonNull String url) {
		synchronized(UrlEntryRepository.locker) {
			var entry = urlEntryRepository.findById(url).orElseGet(() -> {
				var newEntry = new UrlEntry();
				newEntry.setUrl(url);
				newEntry.setCount(0L);
				return newEntry;
			});
			entry.setCount(entry.getCount() + 1);
			urlEntryRepository.save(entry);
			return "ok";
		}
	}
}
