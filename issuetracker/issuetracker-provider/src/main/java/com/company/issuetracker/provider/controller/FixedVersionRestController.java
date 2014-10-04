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

import com.company.issuetracker.core.dto.FixedVersionDTO;
import com.company.issuetracker.provider.service.IssueTrackerProvider;
import com.company.issuetracker.provider.validation.ErrorTranslator;
import com.company.issuetracker.provider.validation.FixedVersionDTOValidator;

@RestController
@RequestMapping("/provider")
@PreAuthorize("hasRole('ROLE_USER')")
public class FixedVersionRestController {

	@Autowired
	private IssueTrackerProvider issueTracker;

	@Autowired
	private FixedVersionDTOValidator fixedVersionDTOValidator;

	@Autowired
	private ErrorTranslator errorTranslator;

	@RequestMapping(value="/issues/{issueId}/fixed-versions/",method=RequestMethod.POST)
	public FixedVersionDTO addFixedVersion(@PathVariable(value="issueId") long issueId,			
			@RequestBody @Valid FixedVersionDTO fixedVersionDTO, 
			BindingResult bindingResult, 
			Locale locale){

		fixedVersionDTOValidator.validate(fixedVersionDTO, bindingResult);

		if(bindingResult.hasErrors()){
			errorTranslator.translate(fixedVersionDTO, bindingResult, locale);			
		}else{
			issueTracker.addFixedVersion(fixedVersionDTO);
		}		

		return fixedVersionDTO;
	}

	@RequestMapping(value="/issues/{issueId}/fixed-versions/",method=RequestMethod.DELETE)
	public FixedVersionDTO deleteFixedVersion(@PathVariable(value="issueId") long issueId,
			@RequestBody @Valid FixedVersionDTO fixedversionDTO, BindingResult bindingResult,
			Locale locale){

		issueTracker.deleteFixedVersion(fixedversionDTO);
		return fixedversionDTO;
	}
}
