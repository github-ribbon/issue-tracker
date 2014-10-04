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

import com.company.issuetracker.core.dto.AffectedVersionDTO;
import com.company.issuetracker.provider.service.IssueTrackerProvider;
import com.company.issuetracker.provider.validation.AffectedVersionDTOValidator;
import com.company.issuetracker.provider.validation.ErrorTranslator;

@RestController
@RequestMapping("/provider")
@PreAuthorize("hasRole('ROLE_USER')")
public class AffectedVersionRestController {

	@Autowired
	private IssueTrackerProvider issueTracker;

	@Autowired
	private AffectedVersionDTOValidator affectedVersionDTOValidator;

	@Autowired
	private ErrorTranslator errorTranslator;

	@RequestMapping(value="/issues/{issueId}/affected-versions/",method=RequestMethod.POST)
	public AffectedVersionDTO addAffectedVersion(@PathVariable(value="issueId") long issueId,			
			@RequestBody @Valid AffectedVersionDTO affectedVersionDTO, 
			BindingResult bindingResult, 
			Locale locale){


		affectedVersionDTOValidator.validate(affectedVersionDTO, bindingResult);

		if(bindingResult.hasErrors()){
			errorTranslator.translate(affectedVersionDTO, bindingResult, locale);			
		}else{
			issueTracker.addAffectedVersion(affectedVersionDTO);
		}		

		return affectedVersionDTO;
	}

	@RequestMapping(value="/issues/{issueId}/affected-versions/",method=RequestMethod.DELETE)
	public AffectedVersionDTO deleteAffectedVersion(@PathVariable(value="issueId") long issueId,
			@RequestBody @Valid AffectedVersionDTO affectedVersionDTO, BindingResult bindingResult,
			Locale locale){

		issueTracker.deleteAffectedVersion(affectedVersionDTO);
		return affectedVersionDTO;
	}
}
