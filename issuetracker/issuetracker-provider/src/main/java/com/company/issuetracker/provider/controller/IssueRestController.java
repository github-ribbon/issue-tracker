package com.company.issuetracker.provider.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.issuetracker.core.domain.Issue;
import com.company.issuetracker.core.dto.IssueDTO;
import com.company.issuetracker.core.dto.Pager;
import com.company.issuetracker.provider.service.IssueTrackerProvider;
import com.company.issuetracker.provider.validation.ErrorTranslator;
import com.company.issuetracker.provider.validation.IssueDTOValidator;

@RestController
@RequestMapping("/provider")
@PreAuthorize("hasRole('ROLE_USER')")
public class IssueRestController {

	@Autowired
	private IssueTrackerProvider issueTracker;

	@Autowired
	private IssueDTOValidator issueDTOValidator;

	@Autowired
	private ErrorTranslator errorTranslator;

	@RequestMapping(value="/issues/",method=RequestMethod.GET)
	public IssueDTO getIssues(
			@RequestParam(required=false, value="status_id") String statusId,
			@RequestParam(required=false, value="priority_id") String priorityId, 
			@RequestParam(required=false, value="issue_type_id") String issueTypeId,
			@RequestParam(required=false, value="reporter") String reporter,
			@RequestParam(required=false, value="assignee") String assignee,
			@RequestParam(required=false, value="owner_id") String ownerId, 
			@RequestParam(required=false, value="project_id") String projectId,	
			Pageable pageable,HttpServletRequest request){

		IssueDTO issueDTO=new IssueDTO();
		issueDTO.setIssue(new Issue());
		issueDTO.getIssue().setStatusId(statusId);
		issueDTO.getIssue().setPriorityId(priorityId);
		issueDTO.getIssue().setIssueTypeId(issueTypeId);
		issueDTO.getIssue().setReporter(reporter);
		issueDTO.getIssue().setAssignee(assignee);
		issueDTO.getIssue().setOwnerId(ownerId);
		issueDTO.getIssue().setProjectId(projectId);		
		issueDTO.setPager(new Pager(pageable));			

		issueTracker.getIssues(issueDTO);

		return issueDTO;
	}	


	@RequestMapping(value="/issues/{issueId}",method=RequestMethod.GET)
	public IssueDTO getIssue(@PathVariable(value="issueId") long issueId, Principal principal){
		IssueDTO issueDTO=new IssueDTO();
		issueDTO.setIssueId(issueId);

		issueTracker.getIssue(issueDTO, principal);

		return issueDTO;
	}	

	@RequestMapping(value="/issues/",method=RequestMethod.POST)
	public IssueDTO addIssue(
			@RequestBody @Valid IssueDTO issueDTO, 
			BindingResult bindingResult, 
			Locale locale){

		issueDTOValidator.validateBeforeCreating(issueDTO, bindingResult);

		if(bindingResult.hasErrors()){
			errorTranslator.translate(issueDTO, bindingResult, locale);			
		}else{
			issueTracker.addIssue(issueDTO);
		}		

		return issueDTO;
	}

	@RequestMapping(value="/issues/",method=RequestMethod.PUT)
	public IssueDTO updateIssue(@RequestBody @Valid IssueDTO issueDTO, 
			BindingResult bindingResult,
			Locale locale){

		issueDTOValidator.validate(issueDTO, bindingResult);

		if(bindingResult.hasErrors()){
			errorTranslator.translate(issueDTO, bindingResult, locale);			
		}else{
			issueTracker.modifyIssue(issueDTO);
		}	

		return issueDTO;
	}

	@RequestMapping(value="/issues/",method=RequestMethod.DELETE)
	public IssueDTO deleteIssue(@RequestBody @Valid IssueDTO issueDTO, 
			BindingResult bindingResult,
			Locale locale){

		issueTracker.deleteIssue(issueDTO);
		return issueDTO;
	}
}
