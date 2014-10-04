package com.company.issuetracker.provider.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.company.issuetracker.core.dto.IssueDTO;
import com.company.issuetracker.provider.domain.IssueEntity;
import com.company.issuetracker.provider.domain.ProjectEntity;
import com.company.issuetracker.provider.domain.ProjectPKEntity;
import com.company.issuetracker.provider.domain.UserEntity;
import com.company.issuetracker.provider.domain.UserPKEntity;

@Component
public class IssueDTOValidator{

	@Autowired
	private UserEntity userEntity;

	@Autowired
	private IssueEntity issueEntity;

	@Autowired
	private ProjectEntity projectEntity;

	/**
	 * Validation needed before inserting, updating and deleting a row. 
	 * This method is called inside the validateBeforeCreating method.
	 * 
	 * @param issueDTO
	 * @param errors
	 */
	public void validate(IssueDTO issueDTO, Errors errors){

		if(((issueDTO.getIssue().getAssignee()!=null)&&(!issueDTO.getIssue().getAssignee().isEmpty()))
				&&(!userEntity.exists(new UserPKEntity(issueDTO.getIssue().getAssignee())))){
			errors.rejectValue("issue.assignee", "error.assigneeNotMatched");
		}

		if(!(issueDTO.getIssue().getStatusId().equals("o")
				||issueDTO.getIssue().getStatusId().equals("r"))){
			errors.rejectValue("issue.statusId", "error.statusNotMatched");
		}

		if(!(issueDTO.getIssue().getPriorityId().equals("a")
				||issueDTO.getIssue().getPriorityId().equals("i"))){
			errors.rejectValue("issue.priorityId", "error.priorityNotMatched");
		}

		if(!(issueDTO.getIssue().getIssueTypeId().equals("b")
				||issueDTO.getIssue().getIssueTypeId().equals("d")
				||issueDTO.getIssue().getIssueTypeId().equals("i")
				||issueDTO.getIssue().getIssueTypeId().equals("n")
				||issueDTO.getIssue().getIssueTypeId().equals("r")
				||issueDTO.getIssue().getIssueTypeId().equals("t")
				)){
			errors.rejectValue("issue.issueTypeId", "error.issueTypeNotMatched");
		}
	}

	/**
	 * Validation needed only before creating a new row.
	 * 
	 * @param issueDTO
	 * @param errors
	 */
	public void validateBeforeCreating(IssueDTO issueDTO, Errors errors) {	

		validate(issueDTO, errors);

		if(!projectEntity.exists(new ProjectPKEntity(issueDTO.getIssue().getOwnerId(),
				issueDTO.getIssue().getProjectId()))){
			errors.rejectValue("issue.projectId", "error.projectNotMatched");
		}		
	}
}

