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
import com.company.issuetracker.core.dto.VersionDTO;


@Controller
@RequestMapping("/versions")
@PreAuthorize("hasRole('ROLE_USER')")
public class VersionController {

	@Autowired
	private IssueTrackerConsumer issueTracker;

	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody VersionDTO addVersion(VersionDTO versionDTO, Principal principal){		
		versionDTO.getVersion().getId().setOwnerId(principal.getName());
		return issueTracker.addVersion(versionDTO);		
	}

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public @ResponseBody VersionDTO deleteVersion(VersionDTO versionDTO, 
			Principal principal, HttpServletResponse response) throws IOException{		
		versionDTO.getVersion().getId().setOwnerId(principal.getName());	
		return issueTracker.deleteVersion(versionDTO);	
	}
}

