package com.company.issuetracker.provider.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.issuetracker.core.dto.VersionDTO;
import com.company.issuetracker.provider.service.IssueTrackerProvider;
import com.company.issuetracker.provider.validation.ErrorTranslator;
import com.company.issuetracker.provider.validation.VersionDTOValidator;

@RestController
@RequestMapping("/provider")
@PreAuthorize("hasRole('ROLE_USER')")
public class VersionRestController {

	@Autowired
	private IssueTrackerProvider issueTracker;

	@Autowired
	private VersionDTOValidator versionDTOValidator;

	@Autowired
	private ErrorTranslator errorTranslator;	

	@RequestMapping(value="/projects/{ownerId}/{projectId}/versions/",method=RequestMethod.POST)
	public VersionDTO addVersion(@PathVariable(value="ownerId") String ownerId,
			@PathVariable(value="projectId") String projectId,
			@RequestBody @Valid VersionDTO versionDTO, 
			BindingResult bindingResult, 
			Locale locale){

		versionDTOValidator.validate(versionDTO, bindingResult);

		if(bindingResult.hasErrors()){
			errorTranslator.translate(versionDTO, bindingResult, locale);			
		}else{
			issueTracker.addVersion(versionDTO);
		}		

		return versionDTO;
	}

	@RequestMapping(value="/projects/{ownerId}/{projectId}/versions/",method=RequestMethod.DELETE)
	public VersionDTO deleteVersion(@PathVariable(value="ownerId") String ownerId,
			@PathVariable(value="projectId") String projectId,
			@RequestBody @Valid VersionDTO versionDTO, BindingResult bindingResult,
			Locale locale){

		issueTracker.deleteVersion(versionDTO);
		return versionDTO;
	}
}
