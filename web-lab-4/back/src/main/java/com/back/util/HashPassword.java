package com.back.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {
    public static String hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }catch(NoSuchAlgorithmException error){
            throw new RuntimeException("Ошибка hash", error);
        }
    }

    public static boolean checkPassword(String password, String hashedPassword){
        return hashPassword(password).equals(hashedPassword);
    }
}
