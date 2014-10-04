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

import com.company.issuetracker.core.dto.ResponseDTO;
import com.company.issuetracker.provider.service.IssueTrackerProvider;
import com.company.issuetracker.provider.validation.ErrorTranslator;
import com.company.issuetracker.provider.validation.ResponseDTOValidator;

@RestController
@RequestMapping("/provider")
@PreAuthorize("hasRole('ROLE_USER')")
public class ResponseRestController {

	@Autowired
	private IssueTrackerProvider issueTracker;

	@Autowired
	private ResponseDTOValidator responseDTOValidator;

	@Autowired
	private ErrorTranslator errorTranslator;

	@RequestMapping(value="/users/{userId}/responses/",method=RequestMethod.GET)
	public ResponseDTO getResponsesByUser(@PathVariable(value="userId") String userId){
		ResponseDTO responseDTO=new ResponseDTO();
		responseDTO.setUserId(userId);

		issueTracker.getResponsesByUser(responseDTO);

		return responseDTO;
	}	

	@RequestMapping(value="/issues/{issueId}/responses/",method=RequestMethod.POST)
	public ResponseDTO addResponse(@PathVariable(value="issueId") long issueId,
			@RequestBody @Valid ResponseDTO responseDTO, 
			BindingResult bindingResult, 
			Locale locale){

		responseDTOValidator.validate(responseDTO, bindingResult);

		if(bindingResult.hasErrors()){
			errorTranslator.translate(responseDTO, bindingResult, locale);			
		}else{
			issueTracker.addResponse(responseDTO);
		}		

		return responseDTO;
	}

	@RequestMapping(value="/issues/{issueId}/responses/",method=RequestMethod.PUT)
	public ResponseDTO updateResponse(@PathVariable(value="issueId") long issueId,
			@RequestBody @Valid ResponseDTO responseDTO, BindingResult bindingResult,
			Locale locale){

		if(bindingResult.hasErrors()){
			errorTranslator.translate(responseDTO, bindingResult, locale);			
		}else{
			issueTracker.updateResponse(responseDTO);
		}	

		return responseDTO;
	}

	@RequestMapping(value="/issues/{issueId}/responses/",method=RequestMethod.DELETE)
	public ResponseDTO deleteResponse(@PathVariable(value="issueId") long issueId,
			@RequestBody @Valid ResponseDTO responseDTO, BindingResult bindingResult,
			Locale locale){

		issueTracker.deleteResponse(responseDTO);
		return responseDTO;
	}
}
