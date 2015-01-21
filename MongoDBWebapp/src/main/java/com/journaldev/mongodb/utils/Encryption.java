package com.journaldev.mongodb.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encryption {

	public static String sha1_encypt(String password)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		System.out.println("password: " + password + ", Encryption: " +  sb.toString());
		return sb.toString();
	}

	public static String generate_salt() {
		SecureRandom random = new SecureRandom();
		String res = new BigInteger(130, random).toString(32);
		System.out.println("Random_Salt: " + res);
		return res;
	}

}
