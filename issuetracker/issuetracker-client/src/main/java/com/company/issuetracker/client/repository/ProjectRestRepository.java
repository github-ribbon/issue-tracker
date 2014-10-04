package com.company.issuetracker.client.repository;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.company.issuetracker.client.util.RestUtils;
import com.company.issuetracker.core.dto.ProjectDTO;

@Repository
public class ProjectRestRepository extends AbstractRestRepository<ProjectDTO> {

	public ProjectDTO findProjects(ProjectDTO projectDTO){

		HashMap<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("ownerId", projectDTO.getOwnerId());
		parameters.put("page", projectDTO.getPager().getPage());
		parameters.put("size", projectDTO.getPager().getSize());
		parameters.put("sort",projectDTO.getPager().getSort());

		return performRestRequest(REST_PROVIDER_HOME+"/projects/?owner_id={ownerId}&page={page}&size={size}&sort={sort}",
				projectDTO, HttpMethod.GET, parameters);
	}
	
	public ProjectDTO findAllProjects(ProjectDTO projectDTO){

		return performRestRequest(REST_PROVIDER_HOME+"/projects/all",
				projectDTO, HttpMethod.GET, new HashMap<String, Object>());
	}
	

	public ProjectDTO findOne(ProjectDTO projectDTO){

		HashMap<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("ownerId", projectDTO.getProject().getId().getOwnerId());
		parameters.put("projectId", projectDTO.getProject().getId().getProjectId());

		return performRestRequest(REST_PROVIDER_HOME+"/projects/{ownerId}/{projectId}/",
				projectDTO, HttpMethod.GET, parameters);		
	}

	public ProjectDTO add(ProjectDTO projectDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/projects/",
				projectDTO, HttpMethod.POST, new HashMap<String, Object>());
	}

	public ProjectDTO update(ProjectDTO projectDTO){ 
		return performRestRequest(REST_PROVIDER_HOME+"/projects/",
				projectDTO, HttpMethod.PUT, new HashMap<String, Object>());
	}

	public ProjectDTO delete(ProjectDTO projectDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/projects/",
				projectDTO, HttpMethod.DELETE, new HashMap<String, Object>());
	}

	@Override
	protected ProjectDTO performRestRequest(String url, ProjectDTO projectDTO,
			HttpMethod httpMethod, HashMap<String, Object> parameters) {

		HttpEntity<Object> request = new HttpEntity<Object>
		(projectDTO,RestUtils.getAuthHeaderForCurrentAuthenticatedUser());

		try{						
			ResponseEntity<ProjectDTO> response = restTemplate		
					.exchange(url, httpMethod, request, ProjectDTO.class, parameters);			

			return response.getBody();	
		}catch(Exception e){
			e.printStackTrace();
			projectDTO.setException(e.getMessage());
		}

		return projectDTO;		
	}
}
