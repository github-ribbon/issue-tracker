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
import com.company.issuetracker.core.dto.AffectedVersionDTO;


@Controller
@RequestMapping("/affected-versions")
@PreAuthorize("hasRole('ROLE_USER')")
public class AffectedVersionController {

	@Autowired
	private IssueTrackerConsumer issueTracker;	

	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody AffectedVersionDTO addAffectedVersion(AffectedVersionDTO affectedVersionDTO, Principal principal){		
		affectedVersionDTO.getAffectedVersion().getId().setOwnerId(principal.getName());
		return issueTracker.addAffectedVersion(affectedVersionDTO);		
	}

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public @ResponseBody AffectedVersionDTO deleteAffectedVersion(AffectedVersionDTO affectedVersionDTO, 
			Principal principal, HttpServletResponse response) throws IOException{		
		affectedVersionDTO.getAffectedVersion().getId().setOwnerId(principal.getName());	
		return issueTracker.deleteAffectedVersion(affectedVersionDTO);	
	}
}

