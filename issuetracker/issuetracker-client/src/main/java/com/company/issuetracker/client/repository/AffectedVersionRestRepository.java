package com.company.issuetracker.client.repository;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.company.issuetracker.client.util.RestUtils;
import com.company.issuetracker.core.dto.AffectedVersionDTO;

@Repository
public class AffectedVersionRestRepository extends AbstractRestRepository<AffectedVersionDTO> {

	private HashMap<String, Object> getParameters(AffectedVersionDTO affectedVersionDTO){
		HashMap<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("issueId", affectedVersionDTO.getAffectedVersion().getId().getIssueId());

		return parameters;
	}

	public AffectedVersionDTO add(AffectedVersionDTO affectedVersionDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/issues/{issueId}/affected-versions/",
				affectedVersionDTO, HttpMethod.POST, getParameters(affectedVersionDTO));
	}

	public AffectedVersionDTO delete(AffectedVersionDTO affectedVersionDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/issues/{issueId}/affected-versions/",
				affectedVersionDTO, HttpMethod.DELETE, getParameters(affectedVersionDTO));
	}

	@Override
	protected AffectedVersionDTO performRestRequest(String url, 
			AffectedVersionDTO affectedVersionDTO,
			HttpMethod httpMethod, HashMap<String, Object> parameters) {

		HttpEntity<Object> request = new HttpEntity<Object>
		(affectedVersionDTO,RestUtils.getAuthHeaderForCurrentAuthenticatedUser());

		try{
			ResponseEntity<AffectedVersionDTO> response = restTemplate
					.exchange(url, httpMethod,request, 
							AffectedVersionDTO.class, parameters);
			return response.getBody();	
		}catch (Exception e){
			affectedVersionDTO.setException(e.getMessage());
		}

		return affectedVersionDTO;		
	}
}
