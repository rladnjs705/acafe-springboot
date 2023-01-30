package com.javadeveloperzone.config.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha512Utils {
	
	public static String encrypt(String str) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(str.getBytes());
			String hex = String.format("%0128x", new BigInteger(1, md.digest()));
			return hex;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
//	public static void main(String[] args) {
//		System.out.println(encrypt("yanadoo!!"));
//	}
}
