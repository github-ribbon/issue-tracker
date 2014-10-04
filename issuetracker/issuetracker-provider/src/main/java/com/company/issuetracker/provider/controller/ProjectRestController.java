package com.company.issuetracker.provider.controller;

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

import com.company.issuetracker.core.domain.Project;
import com.company.issuetracker.core.domain.ProjectPK;
import com.company.issuetracker.core.dto.Pager;
import com.company.issuetracker.core.dto.ProjectDTO;
import com.company.issuetracker.provider.service.IssueTrackerProvider;
import com.company.issuetracker.provider.validation.ErrorTranslator;
import com.company.issuetracker.provider.validation.ProjectDTOValidator;

@RestController
@RequestMapping("/provider")
@PreAuthorize("hasRole('ROLE_USER')")
public class ProjectRestController {

	@Autowired
	private IssueTrackerProvider issueTracker;

	@Autowired
	private ProjectDTOValidator projectDTOValidator;

	@Autowired
	private ErrorTranslator errorTranslator;

	@RequestMapping(value="/projects/",method=RequestMethod.GET)
	public ProjectDTO getProjects(@RequestParam(required=false, value="owner_id") String ownerId,
			Pageable pageable,HttpServletRequest request){	 

		ProjectDTO projectDTO=new ProjectDTO();
		projectDTO.setOwnerId(ownerId);
		projectDTO.setPager(new Pager(pageable));			

		issueTracker.getProjects(projectDTO);

		return projectDTO;
	}	

	@RequestMapping(value="/projects/all",method=RequestMethod.GET)
	public ProjectDTO getAllProjects(){	 

		ProjectDTO projectDTO=new ProjectDTO();	
		issueTracker.getAllProjects(projectDTO);

		return projectDTO;
	}

	
	@RequestMapping(value="/projects/{ownerId}/{projectId}/",method=RequestMethod.GET)
	public ProjectDTO getProjectDetails(@PathVariable(value="ownerId") String ownerId,
			@PathVariable(value="projectId") String projectId){

		ProjectDTO projectDTO=new ProjectDTO();
		projectDTO.setProject(new Project(new ProjectPK(ownerId, projectId)));

		issueTracker.getProject(projectDTO);		

		return projectDTO;
	}

	@RequestMapping(value="/projects/",method=RequestMethod.POST)
	public ProjectDTO addProject(@RequestBody @Valid ProjectDTO projectDTO, 
			BindingResult bindingResult, 
			Locale locale){

		projectDTOValidator.validate(projectDTO, bindingResult);

		if(bindingResult.hasErrors()){
			errorTranslator.translate(projectDTO, bindingResult, locale);			
		}else{
			issueTracker.addProject(projectDTO);
		}		

		return projectDTO;
	}

	@RequestMapping(value="/projects/",method=RequestMethod.PUT)
	public ProjectDTO updateProject(
			@RequestBody @Valid ProjectDTO projectDTO, BindingResult bindingResult,
			Locale locale){

		if(bindingResult.hasErrors()){
			errorTranslator.translate(projectDTO, bindingResult, locale);			
		}else{
			issueTracker.updateProject(projectDTO);
		}	

		return projectDTO;
	}

	@RequestMapping(value="/projects/",method=RequestMethod.DELETE)
	public ProjectDTO deleteProject(HttpServletRequest request,			
			@RequestBody ProjectDTO projectDTO){

		issueTracker.deleteProject(projectDTO);
		return projectDTO;
	}
}
