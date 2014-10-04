package com.company.issuetracker.client.repository;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.company.issuetracker.client.util.RestUtils;
import com.company.issuetracker.core.dto.ResponseDTO;

@Repository
public class ResponseRestRepository extends AbstractRestRepository<ResponseDTO> {

	private HashMap<String,Object> getParameters(ResponseDTO responseDTO){
		HashMap<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("issueId", responseDTO.getResponse().getIssueId());

		return parameters;
	}

	public ResponseDTO findByUser(ResponseDTO responseDTO){

		HashMap<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("userId", responseDTO.getUserId());

		return performRestRequest(REST_PROVIDER_HOME+"/users/{userId}/responses/",
				responseDTO, HttpMethod.GET, parameters);
	}

	public ResponseDTO add(ResponseDTO responseDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/issues/{issueId}/responses/",
				responseDTO, HttpMethod.POST,getParameters(responseDTO));
	}

	public ResponseDTO update(ResponseDTO responseDTO){				
		return performRestRequest(REST_PROVIDER_HOME+"/issues/{issueId}/responses/",
				responseDTO, HttpMethod.PUT,getParameters(responseDTO));
	}

	public ResponseDTO delete(ResponseDTO responseDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/issues/{issueId}/responses/",
				responseDTO, HttpMethod.DELETE,getParameters(responseDTO));
	}

	@Override
	protected ResponseDTO performRestRequest(String url, ResponseDTO responseDTO,
			HttpMethod httpMethod, HashMap<String, Object> parameters) {

		HttpEntity<Object> request = new HttpEntity<Object>
		(responseDTO,RestUtils.getAuthHeaderForCurrentAuthenticatedUser());

		try{
			ResponseEntity<ResponseDTO> response = restTemplate
					.exchange(url, httpMethod, request, ResponseDTO.class, parameters);
			return response.getBody();	
		}catch(Exception e){
			responseDTO.setException(e.getMessage());
		}

		return responseDTO;
	}
}