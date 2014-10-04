package com.company.issuetracker.client.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.issuetracker.client.service.IssueTrackerConsumer;
import com.company.issuetracker.core.dto.ResponseDTO;


@Controller
@RequestMapping("/responses")
@PreAuthorize("hasRole('ROLE_USER')")
public class ResponseController {

	@Autowired
	private IssueTrackerConsumer issueTracker;	

	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody ResponseDTO addResponse(ResponseDTO responseDTO, Principal principal){		
		responseDTO.getResponse().setUserId(principal.getName());		
		return issueTracker.addResponse(responseDTO);		
	}

	@RequestMapping(value="/save",method=RequestMethod.POST)
	public @ResponseBody ResponseDTO saveResponse(ResponseDTO responseDTO, 
			Principal principal, HttpServletResponse response){		
		responseDTO.getResponse().setUserId(principal.getName());			
		return issueTracker.updateResponse(responseDTO);			
	}

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public @ResponseBody ResponseDTO deleteResponse(ResponseDTO responseDTO, 
			Principal principal, HttpServletResponse response) throws IOException{		
		responseDTO.getResponse().setUserId(principal.getName());	
		return issueTracker.deleteResponse(responseDTO);	
	}
}

