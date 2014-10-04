package com.company.issuetracker.client.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.issuetracker.client.service.IssueTrackerConsumer;
import com.company.issuetracker.core.domain.Issue;
import com.company.issuetracker.core.domain.Project;
import com.company.issuetracker.core.domain.ProjectPK;
import com.company.issuetracker.core.dto.IssueDTO;
import com.company.issuetracker.core.dto.Pager;
import com.company.issuetracker.core.dto.ProjectDTO;
import com.company.issuetracker.core.dto.UserDTO;


@Controller
@RequestMapping("/issues")
@PreAuthorize("hasRole('ROLE_USER')")
public class IssueController {

	@Autowired
	private IssueTrackerConsumer issueTracker;	

	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String displayIssues(
			@RequestParam(required=false, value="status_id") String statusId,
			@RequestParam(required=false, value="priority_id") String priorityId, 
			@RequestParam(required=false, value="issue_type_id") String issueTypeId,
			@RequestParam(required=false, value="reporter") String reporter,
			@RequestParam(required=false, value="assignee") String assignee,
			@RequestParam(required=false, value="owner_id") String ownerId, 
			@RequestParam(required=false, value="project_id") String projectId,	
			Pageable pageable,
			Principal principal, Model model){
		
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
		
		model.addAttribute("issueDTO", issueTracker.getIssues(issueDTO));
		model.addAttribute("userDTO", issueTracker.getAllUsers(new UserDTO()));
		model.addAttribute("projectDTO", issueTracker.getAllProjects(new ProjectDTO()));
		
		return "issueList";
	}	

	@RequestMapping(value="/new",method=RequestMethod.GET)
	public String displayNewIssueForm(
			@RequestParam(required=true, value="owner_id") String ownerId,
			@RequestParam(required=true, value="project_id") String projectId,
			Model model, HttpServletResponse response) throws IOException{

		ProjectDTO projectDTO=new ProjectDTO();
		projectDTO.setProject(new Project(new ProjectPK(ownerId, projectId)));

		projectDTO=issueTracker.getProject(projectDTO);

		if(projectDTO.getProject()!=null){
			model.addAttribute("projectDTO", projectDTO);
			model.addAttribute("userDTO", issueTracker.getAllUsers(new UserDTO()));

			return "newIssueForm";
		}else{
			response.sendError(400, "Incorrect project id");

			return null;
		}				
	}

	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody IssueDTO addIssue(IssueDTO issueDTO, Principal principal){		
		issueDTO.getIssue().setReporter(principal.getName());		
		return issueTracker.addIssue(issueDTO);
	}

	@RequestMapping(value="/view",method=RequestMethod.GET)
	public String displayIssueDetails(
			@RequestParam(value="issue_id", required=true) long issueId,
			Model model){

		IssueDTO issueDTO=new IssueDTO();
		issueDTO.setIssue(new Issue(issueId));

		model.addAttribute("issueDTO", issueTracker.getIssue(issueDTO));
		model.addAttribute("userDTO", issueTracker.getAllUsers(new UserDTO()));

		return "issueView";		
	}	

	@RequestMapping(value="/save",method=RequestMethod.POST)
	public @ResponseBody IssueDTO saveIssue(IssueDTO issueDTO, 
			Principal principal, HttpServletResponse response){
		return issueTracker.modifyIssue(issueDTO);			
	}	

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public @ResponseBody IssueDTO deleteIssue(IssueDTO issueDTO, 
			Principal principal, HttpServletResponse response){		
		return issueTracker.deleteIssue(issueDTO);	
	}	
}

