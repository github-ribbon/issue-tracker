package com.company.issuetracker.client.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.issuetracker.client.service.IssueTrackerConsumer;
import com.company.issuetracker.core.domain.User;
import com.company.issuetracker.core.domain.UserPK;
import com.company.issuetracker.core.dto.UserDTO;


@Controller
public class UserController {

	@Autowired
	private IssueTrackerConsumer issueTracker;

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(@RequestParam(value="reg",required=false) String registered){
		return "loginPage";
	}	

	@RequestMapping(value="/registration",method=RequestMethod.GET)
	public String displayRegistrationForm(){		
		return "registration";
	}

	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public @ResponseBody UserDTO register(UserDTO userDTO){
		return issueTracker.registerUser(userDTO);		
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/account",method=RequestMethod.GET)
	public String displayAccountDetails(Model model,Principal principal){

		UserDTO userDTO=new UserDTO();
		userDTO.setUser(new User(new UserPK(principal.getName())));

		model.addAttribute("userDTO", issueTracker.getUser(userDTO));

		return "account";		
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/account",method=RequestMethod.POST)
	public @ResponseBody UserDTO saveAccountDetails(Principal principal,UserDTO userDTO){

		UserDTO response=issueTracker.updateUser(userDTO);

		if((response.getException()==null)&&(response.getErrors()==null)){
			SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);			
		}

		return response;		
	}	
}

