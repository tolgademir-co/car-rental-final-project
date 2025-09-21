package com.tolgademir.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HashUtil -> Provides SHA-256 hashing utility
 * // SHA-256 hash işlemleri için yardımcı sınıf
 */
public class HashUtil {

    /**
     * Generate SHA-256 hash of given input
     * // Girilen string için SHA-256 hash üretir
     */
    public static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            // Byte dizisini hex string’e çevir
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("❌ Error generating SHA-256 hash", e);
        }
    }
}