package com.company.issuetracker.provider.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.issuetracker.core.domain.User;
import com.company.issuetracker.core.domain.UserPK;
import com.company.issuetracker.core.dto.UserDTO;
import com.company.issuetracker.provider.service.IssueTrackerProvider;
import com.company.issuetracker.provider.validation.ErrorTranslator;
import com.company.issuetracker.provider.validation.UserDTOValidator;

@RestController
@RequestMapping("/provider/users")
public class UserRestController {

	@Autowired
	private IssueTrackerProvider issueTracker;

	@Autowired
	private UserDTOValidator userDTOValidator;

	@Autowired
	private ErrorTranslator errorTranslator;

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/auth",method=RequestMethod.POST)
	public void auth(){

	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public UserDTO getUser(@PathVariable String userId){

		UserDTO userDTO=new UserDTO();
		userDTO.setUser(new User(new UserPK(userId)));

		issueTracker.getUser(userDTO);

		return userDTO;
	}	

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/",method=RequestMethod.GET)
	public UserDTO getUsers(
			Locale locale){	

		UserDTO userDTO=new UserDTO();
		issueTracker.getAllUsers(userDTO);

		return userDTO;
	}	

	@RequestMapping(value="/",method=RequestMethod.POST)
	public UserDTO registerUser(@RequestBody @Valid UserDTO userDTO, 
			BindingResult bindingResult, 
			Locale locale){

		userDTOValidator.validate(userDTO, bindingResult);

		if(bindingResult.hasErrors()){
			errorTranslator.translate(userDTO, bindingResult, locale);	

		}else{
			issueTracker.registerUser(userDTO);
		}		

		return userDTO;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/",method=RequestMethod.PUT)
	public UserDTO updateUser(
			@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult,
			HttpServletRequest request,Principal principal,Locale locale){

		userDTO.getUser().setId(new UserPK(principal.getName()));

		if(bindingResult.hasFieldErrors("user.name")
				||bindingResult.hasFieldErrors("user.surname")
				||bindingResult.hasFieldErrors("user.password")){
			errorTranslator.translate(userDTO, bindingResult, locale);			
		}else{
			issueTracker.updateUser(userDTO);
		}	

		return userDTO;
	}	
}
