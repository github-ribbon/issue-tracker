package com.company.issuetracker.provider.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class HashCodeUtil {  

	public static String getHashPassword(String password) {  
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		String hashedPassword = passwordEncoder.encode(password);  

		return hashedPassword;  
	} 
}  
