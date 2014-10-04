package com.company.issuetracker.core.dto;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.company.issuetracker.core.domain.Response;

public class ResponseDTO extends AbstractDTO {

	private static final long serialVersionUID = 1L;

	@Valid
	private Response response;

	private Page<Response> responses;

	private String userId;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Page<Response> getResponses() {
		return responses;
	}

	public void setResponses(Page<Response> responses) {
		this.responses = responses;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
