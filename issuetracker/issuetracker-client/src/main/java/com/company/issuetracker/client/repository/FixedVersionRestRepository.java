package com.company.issuetracker.client.repository;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.company.issuetracker.client.util.RestUtils;
import com.company.issuetracker.core.dto.FixedVersionDTO;

@Repository
public class FixedVersionRestRepository extends AbstractRestRepository<FixedVersionDTO> {

	private HashMap<String, Object> getParameters(FixedVersionDTO fixedVersionDTO){
		HashMap<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("issueId", fixedVersionDTO.getFixedVersion().getId().getIssueId());

		return parameters;
	}

	public FixedVersionDTO add(FixedVersionDTO fixedVersionDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/issues/{issueId}/fixed-versions/",
				fixedVersionDTO, HttpMethod.POST, getParameters(fixedVersionDTO));
	}

	public FixedVersionDTO delete(FixedVersionDTO fixedVersionDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/issues/{issueId}/fixed-versions/",
				fixedVersionDTO, HttpMethod.DELETE, getParameters(fixedVersionDTO));
	}

	@Override
	protected FixedVersionDTO performRestRequest(String url,
			FixedVersionDTO fixedVersionDTO, HttpMethod httpMethod,
			HashMap<String, Object> parameters) {

		HttpEntity<Object> request = new HttpEntity<Object>
		(fixedVersionDTO,RestUtils.getAuthHeaderForCurrentAuthenticatedUser());

		try{
			ResponseEntity<FixedVersionDTO> response = restTemplate
					.exchange(url, httpMethod, request, 
							FixedVersionDTO.class, parameters);
			return response.getBody();	
		}catch(Exception e){
			fixedVersionDTO.setException(e.getMessage());
		}

		return fixedVersionDTO;
	}
}
