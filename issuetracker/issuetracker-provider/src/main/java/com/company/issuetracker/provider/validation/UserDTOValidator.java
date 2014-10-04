package com.company.issuetracker.provider.validation;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.company.issuetracker.core.dto.UserDTO;
import com.company.issuetracker.provider.domain.UserEntity;
import com.company.issuetracker.provider.domain.UserPKEntity;

@Component
public class UserDTOValidator{

	@Autowired
	private DozerBeanMapper mapper;

	@Autowired
	private UserEntity userEntity;

	public void validate(UserDTO userDTO, Errors errors) {

		if(userEntity.exists(mapper.map(userDTO.getUser().getId(),UserPKEntity.class))){
			errors.rejectValue("user.id.userId", "error.userExists");
		}
	}
}

