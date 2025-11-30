package org.hinst.web.counter;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomUtil {
	/** https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Number/MAX_SAFE_INTEGER */
	public static final long MAX_SAFE_INTEGER = 1L << 52 - 1;

	public static long getRandomLong(long limit) {
		try {
			return SecureRandom.getInstanceStrong().nextLong(limit);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Cannot generate random long number", e);
		}
	}

	public static int getRandomInt(int limit) {
		try {
			return SecureRandom.getInstanceStrong().nextInt(limit);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Cannot generate random int number", e);
		}
	}
}
