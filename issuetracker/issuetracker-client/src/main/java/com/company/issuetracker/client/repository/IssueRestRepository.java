package com.company.issuetracker.client.repository;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.company.issuetracker.client.util.RestUtils;
import com.company.issuetracker.core.dto.IssueDTO;

@Repository
public class IssueRestRepository extends AbstractRestRepository<IssueDTO> {

	private HashMap<String, Object> getParameters(IssueDTO issueDTO){
		HashMap<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("issueId", issueDTO.getIssue().getIssueId());

		return parameters;
	}

	public IssueDTO findIssues(IssueDTO issueDTO){

		HashMap<String, Object> parameters=new HashMap<String, Object>();

		parameters.put("status_id", issueDTO.getIssue().getStatusId());
		parameters.put("priority_id", issueDTO.getIssue().getPriorityId());
		parameters.put("issue_type_id", issueDTO.getIssue().getIssueTypeId());
		parameters.put("reporter", issueDTO.getIssue().getReporter());
		parameters.put("assignee", issueDTO.getIssue().getAssignee());
		parameters.put("owner_id", issueDTO.getIssue().getOwnerId());
		parameters.put("project_id", issueDTO.getIssue().getProjectId());

		parameters.put("page", issueDTO.getPager().getPage());
		parameters.put("size", issueDTO.getPager().getSize());
		parameters.put("sort",issueDTO.getPager().getSort());


		return performRestRequest(REST_PROVIDER_HOME+"/issues/?page={page}&size={size}&sort={sort}"
				+ "&status_id={status_id}&priority_id={priority_id}&issue_type_id={issue_type_id}"
				+ "&reporter={reporter}&assignee={assignee}&owner_id={owner_id}&project_id={project_id}",
				issueDTO, HttpMethod.GET, parameters);
	}

	public IssueDTO findOne(IssueDTO issueDTO){	
		return performRestRequest(REST_PROVIDER_HOME+"/issues/{issueId}",
				issueDTO, HttpMethod.GET, getParameters(issueDTO));				
	}

	public IssueDTO add(IssueDTO responseDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/issues/",
				responseDTO, HttpMethod.POST,new HashMap<String, Object>());
	}

	public IssueDTO update(IssueDTO responseDTO){				
		return performRestRequest(REST_PROVIDER_HOME+"/issues/",
				responseDTO, HttpMethod.PUT,new HashMap<String, Object>());
	}

	public IssueDTO delete(IssueDTO responseDTO){
		return performRestRequest(REST_PROVIDER_HOME+"/issues/",
				responseDTO, HttpMethod.DELETE,new HashMap<String, Object>());
	}

	@Override
	protected IssueDTO performRestRequest(String url, IssueDTO issueDTO,
			HttpMethod httpMethod, HashMap<String, Object> parameters) {

		HttpEntity<Object> request = new HttpEntity<Object>
		(issueDTO,RestUtils.getAuthHeaderForCurrentAuthenticatedUser());

		try{
			ResponseEntity<IssueDTO> response = restTemplate
					.exchange(url, httpMethod, request,
							IssueDTO.class, parameters);
			return response.getBody();	
		}
		catch (Exception e){
			issueDTO.setException(e.getMessage());	       
		} 

		return issueDTO;
	}
}