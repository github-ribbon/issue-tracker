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
import com.company.issuetracker.core.dto.FixedVersionDTO;


@Controller
@RequestMapping("/fixed-versions")
@PreAuthorize("hasRole('ROLE_USER')")
public class FixedVersionController {

	@Autowired
	private IssueTrackerConsumer issueTracker;	

	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody FixedVersionDTO addFixedVersion(FixedVersionDTO fixedVersionDTO, Principal principal){		
		fixedVersionDTO.getFixedVersion().getId().setOwnerId(principal.getName());
		return issueTracker.addFixedVersion(fixedVersionDTO);		
	}

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public @ResponseBody FixedVersionDTO deleteFixedVersion(FixedVersionDTO fixedVersionDTO, 
			Principal principal, HttpServletResponse response) throws IOException{		
		fixedVersionDTO.getFixedVersion().getId().setOwnerId(principal.getName());	
		return issueTracker.deleteFixedVersion(fixedVersionDTO);	
	}
}

