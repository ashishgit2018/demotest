package com.test.autothon.common;


import java.security.SecureRandom;

/**
 * @author Rahul_Goyal
 */
public class RandomGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String INTEGERS = "0123456789";
    private static final String ALPHANUMERIC = INTEGERS + ALPHABET;
    private static SecureRandom rnd = new SecureRandom();

    private static String randomGenerator(int len, String generator) {
        StringBuilder sbf = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sbf.append(generator.charAt(rnd.nextInt(generator.length())));
        return sbf.toString();
    }

    public static String generateRandonIntegerValue(int len) {
        return randomGenerator(len, INTEGERS);
    }

    public static String generateRandomStringValue(int len) {
        return randomGenerator(len, ALPHABET);
    }

    public static String generateRandomAlphaNumericValue(int len) {
        return randomGenerator(len, ALPHANUMERIC);
    }


}
