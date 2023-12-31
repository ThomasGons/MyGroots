package com.springboot.mygroots.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.PersonService;

public class Utils {
	
	/**
	 * Encodes a string using the sha256 hash algorithm 
	 * @param password
	 * @return the encoded text
	 */
	public static String encode(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(text.getBytes());
            byte[] hash = digest.digest();
            // Converting hash to hexadecimal representation
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static Account AuthentificatedUser(String token, String id, PersonService personService, AccountService accountService) {
    	Person p = personService.getPersonById(id);
		if (p != null) {
			Account acc = accountService.getAccountByPerson(p);
			if (acc != null && acc.isAuthenticated(token)) {
				return acc;
			}
		}
		return null;
    }
	
}
