package com.company.issuetracker.client.util;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;

public class RestUtils {

	/**
	 * Returns the HTTP Auth Header for the current authenticated user
	 * 
	 *@return
	 */
	public static HttpHeaders getAuthHeaderForCurrentAuthenticatedUser(){

		return getAuthHeader(SecurityContextHolder.
				getContext().
				getAuthentication().
				getName()
				+":"+((UsernamePasswordAuthenticationToken) SecurityContextHolder.
						getContext().
						getAuthentication().getCredentials()).getCredentials().toString());	
	}

	/**
	 * Returns the HTTP Auth Header for the given user name and password
	 * 
	 * @param auth the user name and password, i.e. "user1:plain_password1"
	 * @return
	 */
	public static HttpHeaders getAuthHeader(String auth) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		byte[] encodedAuthorization = Base64.encode(auth.getBytes());
		headers.add("Authorization", "Basic " + new String(encodedAuthorization));

		return headers;
	}
}
