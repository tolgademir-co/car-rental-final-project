package com.tolgademir.util;

public class HashTest {

    public static void main(String[] args) {

        String password = "admin123";
        String hash = HashUtil.sha256(password);

        System.out.println("Password: " + password);
        System.out.println("SHA-256: " + hash);
    }
}