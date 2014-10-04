package com.company.issuetracker.client.controller;

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
import com.company.issuetracker.core.domain.Project;
import com.company.issuetracker.core.domain.ProjectPK;
import com.company.issuetracker.core.dto.Pager;
import com.company.issuetracker.core.dto.ProjectDTO;
import com.company.issuetracker.core.dto.UserDTO;


@Controller
@RequestMapping("/projects")
@PreAuthorize("hasRole('ROLE_USER')")
public class ProjectController {

	@Autowired
	private IssueTrackerConsumer issueTracker;

	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String displayProjects(
			@RequestParam(value="owner_id", required=false) String ownerId,
			Pageable pageable,
			Principal principal, Model model){

		ProjectDTO projectDTO=new ProjectDTO();		
		projectDTO.setOwnerId(ownerId);
		projectDTO.setPager(new Pager(pageable));

		model.addAttribute("projectDTO", issueTracker.getProjects(projectDTO));
		model.addAttribute("userDTO", issueTracker.getAllUsers(new UserDTO()));

		return "projectList";
	}		

	@RequestMapping(value="/new",method=RequestMethod.GET)
	public String displayNewProjectForm(Model model){
		model.addAttribute("projectDTO", new ProjectDTO());		
		return "newProjectForm";		
	}

	@RequestMapping(value="/view",method=RequestMethod.GET)
	public String displayProjectDetails(
			@RequestParam(value="owner_id", required=true) String ownerId,
			@RequestParam(value="project_id", required=true) String projectId,
			Model model){

		ProjectDTO projectDTO=new ProjectDTO();
		projectDTO.setProject(new Project(new ProjectPK(ownerId, projectId)));

		model.addAttribute("projectDTO", issueTracker.getProject(projectDTO));	

		return "projectView";		
	}

	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody ProjectDTO addProject(ProjectDTO projectDTO, 
			Principal principal){		
		projectDTO.getProject().getId().setOwnerId(principal.getName());		
		return issueTracker.addProject(projectDTO);		
	}

	@RequestMapping(value="/save",method=RequestMethod.POST)
	public @ResponseBody ProjectDTO saveProject(ProjectDTO projectDTO, 
			Principal principal, HttpServletResponse response) {		
		projectDTO.getProject().getId().setOwnerId(principal.getName());			
		return issueTracker.updateProject(projectDTO);			
	}

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public @ResponseBody ProjectDTO deleteProject(ProjectDTO projectDTO, 
			Principal principal, HttpServletResponse response) {		
		projectDTO.getProject().getId().setOwnerId(principal.getName());	
		return issueTracker.deleteProject(projectDTO);	
	}
}

