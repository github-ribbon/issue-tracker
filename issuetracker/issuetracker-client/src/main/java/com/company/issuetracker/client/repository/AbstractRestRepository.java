package com.company.issuetracker.client.repository;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractRestRepository<T> {

	/**
	 * The home path for the RESTful WebServices provider
	 */
	public static final String REST_PROVIDER_HOME="http://localhost:8080/issuetracker-provider/provider";

	@Autowired
	protected RestTemplate restTemplate;

	protected abstract T performRestRequest(String url, T dto, HttpMethod httpMethod, HashMap<String, Object> parameters); 
}
