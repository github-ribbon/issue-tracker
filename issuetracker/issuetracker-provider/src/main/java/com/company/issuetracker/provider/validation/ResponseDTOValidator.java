package com.company.issuetracker.provider.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.company.issuetracker.core.dto.ResponseDTO;
import com.company.issuetracker.provider.domain.IssueEntity;


@Component
public class ResponseDTOValidator{

	@Autowired
	private IssueEntity issueEntity;

	public void validate(ResponseDTO responseDTO, Errors errors) {

		if(!issueEntity.exists(responseDTO.getResponse().getIssueId())){
			errors.rejectValue("response.content", "error.issueNotMatched");
		}
	}
}

