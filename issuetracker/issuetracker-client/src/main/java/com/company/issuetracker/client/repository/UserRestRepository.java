package com.company.issuetracker.client.repository;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.company.issuetracker.client.util.RestUtils;
import com.company.issuetracker.core.dto.UserDTO;

@Repository
public class UserRestRepository extends AbstractRestRepository<UserDTO>{

	private HashMap<String, Object> getParameters(UserDTO userDTO){
		HashMap<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("userId", userDTO.getUser().getId().getUserId());

		return parameters;
	}

	public UserDTO findAllUsers(UserDTO userDTO){
		HttpEntity<Object> request = new HttpEntity<Object>
		(RestUtils.getAuthHeaderForCurrentAuthenticatedUser());

		return performRestRequest(REST_PROVIDER_HOME+"/users/",
				request, userDTO, HttpMethod.GET, new HashMap<String, Object>());		

	}

	public UserDTO find(UserDTO userDTO){
		HttpEntity<Object> request = new HttpEntity<Object>
		(RestUtils.getAuthHeaderForCurrentAuthenticatedUser());

		return performRestRequest(REST_PROVIDER_HOME+"/users/{userId}",
				request, userDTO, HttpMethod.GET, getParameters(userDTO));		

	}

	public UserDTO findAuth(UserDTO userDTO){			
		HttpEntity<Object> request = new HttpEntity<Object>
		(RestUtils.getAuthHeader(userDTO.getUser().getId().getUserId()+":"+userDTO.getUser().getPassword()));

		return performRestRequest(REST_PROVIDER_HOME+"/users/auth",
				request, userDTO, HttpMethod.POST, new HashMap<String, Object>());	
	}

	public UserDTO add(UserDTO userDTO){	
		HttpEntity<Object> request = new HttpEntity<Object>(userDTO);

		return performRestRequest(REST_PROVIDER_HOME+"/users/",
				request, userDTO, HttpMethod.POST, new HashMap<String, Object>());

	}

	public UserDTO update(UserDTO userDTO){		
		return performRestRequest(REST_PROVIDER_HOME+"/users/",
				userDTO, HttpMethod.PUT, new HashMap<String, Object>());	
	}	

	@Override
	protected UserDTO performRestRequest(String url, UserDTO userDTO,
			HttpMethod httpMethod, HashMap<String, Object> parameters) {

		HttpEntity<Object> request = new HttpEntity<Object>
		(userDTO,RestUtils.getAuthHeaderForCurrentAuthenticatedUser());

		return performRestRequest(url, request, userDTO, httpMethod, parameters);
	}

	protected UserDTO performRestRequest(String url, HttpEntity<Object> request, UserDTO userDTO,
			HttpMethod httpMethod, HashMap<String, Object> parameters) {

		try{
			ResponseEntity<UserDTO> response = restTemplate
					.exchange(url, httpMethod, request, UserDTO.class, parameters);
			return response.getBody();	
		}catch(Exception e){
			userDTO.setException(e.getMessage());
		}

		return userDTO;
	}
}
