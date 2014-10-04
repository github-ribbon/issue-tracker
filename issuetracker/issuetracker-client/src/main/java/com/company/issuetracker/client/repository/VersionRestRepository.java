package com.company.issuetracker.client.repository;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.company.issuetracker.client.util.RestUtils;
import com.company.issuetracker.core.dto.VersionDTO;

@Repository
public class VersionRestRepository extends AbstractRestRepository<VersionDTO> {

	private HashMap<String,Object> getParameters(VersionDTO versionDTO){
		HashMap<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("ownerId", versionDTO.getVersion().getId().getOwnerId());
		parameters.put("projectId", versionDTO.getVersion().getId().getProjectId());

		return parameters;
	}

	public VersionDTO add(VersionDTO versionDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/projects/{ownerId}/{projectId}/versions/",
				versionDTO, HttpMethod.POST, getParameters(versionDTO));
	}

	public VersionDTO delete(VersionDTO versionDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/projects/{ownerId}/{projectId}/versions/",
				versionDTO, HttpMethod.DELETE, getParameters(versionDTO));
	}

	@Override
	protected VersionDTO performRestRequest(String url, VersionDTO versionDTO,
			HttpMethod httpMethod, HashMap<String, Object> parameters) {

		HttpEntity<Object> request = new HttpEntity<Object>
		(versionDTO,RestUtils.getAuthHeaderForCurrentAuthenticatedUser());

		try{
			ResponseEntity<VersionDTO> response = restTemplate		
					.exchange(url, httpMethod, request, 
							VersionDTO.class, parameters);
			return response.getBody();
		}catch(Exception e){
			versionDTO.setException(e.getMessage());
		}

		return versionDTO;
	}
}
