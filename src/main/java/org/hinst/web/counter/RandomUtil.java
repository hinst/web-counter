package org.hinst.web.counter;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomUtil {
	public static long getRandomLong() {
		try {
			return SecureRandom.getInstanceStrong().nextLong();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Cannot generate random long number", e);
		}
	}

	public static int getRandomInt(int bound) {
		try {
			return SecureRandom.getInstanceStrong().nextInt(bound);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Cannot generate random int number", e);
		}
	}
}
