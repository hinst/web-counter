package org.hinst.web.counter;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class RiddleManager {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final int PRIME_NUMBER_LIMIT = 2000;
	private static final int STEP_COUNT = 2;
	private static final long PRODUCT_LIMIT = 1_000_000_000;
	private static final long LIFETIME_MINUTES = 5;

	@Value("classpath:primeNumbers.json")
	private Resource primeNumbersFile;
	private int[] primeNumbers;

	private final RiddleEntryRepository riddleEntryRepository;

	@PostConstruct
	public void init() {
		try {
			primeNumbers = new ObjectMapper().readerFor(int[].class)
				.readValue(primeNumbersFile.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException("Cannot read prime numbers", e);
		}
		if (primeNumbers.length < PRIME_NUMBER_LIMIT)
			throw new IllegalStateException("Need at least " + PRIME_NUMBER_LIMIT + " prime numbers");
		primeNumbers = Arrays.copyOf(primeNumbers, PRIME_NUMBER_LIMIT);
	}

	public RiddleEntry generateRiddle() {
		long product = 1;
		for (var i = 0; i < STEP_COUNT; ++i) {
			var index = RandomUtil.getRandomInt(primeNumbers.length);
			product = (product * primeNumbers[index]) % PRODUCT_LIMIT;
		}
		var riddle = new RiddleEntry(
			RandomUtil.getRandomLong(), Instant.now(),
			product, STEP_COUNT, primeNumbers.length, PRODUCT_LIMIT
		);
		riddleEntryRepository.saveNew(riddle);
		return riddle;
	}

	@Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
	@Transactional
	protected void deleteOldRiddles() {
		var dateTime = Instant.now().minus(LIFETIME_MINUTES, ChronoUnit.MINUTES);
		var countOfDeleted = riddleEntryRepository.deleteOlder(dateTime);
		if (countOfDeleted > 0)
			logger.info("Deleted old riddles: {}", countOfDeleted);
	}

	public int[] getPrimeNumbers() {
		return primeNumbers;
	}
}
