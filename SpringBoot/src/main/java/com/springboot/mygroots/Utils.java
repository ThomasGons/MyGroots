package com.springboot.mygroots;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

	public static StringBuilder encode(String password) {
        try {
        	System.out.println("encode");
            // Creation instance de MessageDigest pour SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Mise a jour du digest avec les donnees
            digest.update(password.getBytes());

            // Récupération valeur de hash
            byte[] hash = digest.digest();

            // Conversion hash en representation hexadecimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Affichage du hash SHA256
            System.out.println("SHA256 hash de \"" + password + "\": " + hexString.toString());
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
