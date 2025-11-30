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
	private static final int PRIME_NUMBER_LIMIT = 1000;
	private static final int STEP_COUNT = 2;
	private static final long PRODUCT_LIMIT = 1_000_000_000;
	private static final long LIFETIME_MINUTES = 2;

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

	public RiddleEntry generate() {
		long product = 1;
		for (var i = 0; i < STEP_COUNT; ++i) {
			var index = RandomUtil.getRandomInt(primeNumbers.length);
			product = (product * primeNumbers[index]) % PRODUCT_LIMIT;
		}
		var riddle = new RiddleEntry(
			RiddleEntry.generateId(), Instant.now(),
			product, STEP_COUNT, primeNumbers.length, PRODUCT_LIMIT
		);
		riddleEntryRepository.saveNew(riddle);
		return riddle;
	}

	@Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
	@Transactional
	protected void deleteOld() {
		var dateTime = Instant.now().minus(LIFETIME_MINUTES, ChronoUnit.MINUTES);
		var countOfDeleted = riddleEntryRepository.deleteOlder(dateTime);
		if (countOfDeleted > 0)
			logger.info("Deleted old riddles: {}", countOfDeleted);
	}

	public void verifyAnswer(long id, int[] indexes) {
		var riddleEntry = riddleEntryRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Riddle not found: " + id));
		if (indexes.length != riddleEntry.getStepCount())
			throw new IllegalArgumentException("Count of indexes must match count of steps");
		long product = 1;
		for (var index : indexes) {
			var item = primeNumbers[index];
			product = (product * item) % PRODUCT_LIMIT;
		}
		var isCorrect = product == riddleEntry.getProduct();
		if (!isCorrect)
			throw new IllegalArgumentException("Wrong answer");
		riddleEntryRepository.delete(riddleEntry);
	}

	public int[] getPrimeNumbers() {
		return primeNumbers;
	}
}
