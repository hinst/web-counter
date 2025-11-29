package org.hinst.web.counter;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/riddle")
@RequiredArgsConstructor
public class RiddleController {
	private static final int PRIME_NUMBER_LIMIT = 1000;
	private static final int STEP_COUNT = 3;
	private static final long PRODUCT_LIMIT = 1_000_000;

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

	@GetMapping("/prime-numbers")
	public String getMethodName(@RequestParam String param) throws IOException {
		return primeNumbersFile.getContentAsString(Objects.requireNonNull(StandardCharsets.UTF_8));
	}

	@GetMapping("/generate")
	@ResponseBody
	public RiddleEntry generateRiddle() {
		long product = 1;
		for (var i = 0; i < STEP_COUNT; ++i) {
			var index = RandomUtil.getRandomInt(primeNumbers.length);
			product = (product * primeNumbers[index]) % PRODUCT_LIMIT;
		}
		var riddle = new RiddleEntry(RandomUtil.getRandomLong(), Instant.now(), product);
		riddleEntryRepository.saveNew(riddle);
		return riddle;
	}
}
