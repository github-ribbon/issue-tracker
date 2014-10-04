package com.company.issuetracker.provider.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.company.issuetracker.core.dto.AbstractDTO;
import com.company.issuetracker.core.validation.ErrorMessage;

@Component
public class ErrorTranslator {

	@Autowired
	private MessageSource messages;

	private String getDtoName(AbstractDTO dto) {

		String concreteDTO=dto.getClass().getSimpleName();

		if(concreteDTO.equals("UserDTO"))
			return "userDto";
		else if(concreteDTO.equals("ProjectDTO"))
			return "projectDto";
		else if(concreteDTO.equals("VersionDTO"))
			return "versionDto";
		else if(concreteDTO.equals("IssueDTO"))
			return "issueDto";
		else if(concreteDTO.equals("ResponseDTO"))
			return "responseDto";
		else
			throw new IllegalArgumentException("Incorrect DTO class");
	}

	public void translate(AbstractDTO dto,BindingResult bindingResult,Locale locale){

		List<FieldError> objectErrors=bindingResult.getFieldErrors();
		//		getAllErrors();
		List<ErrorMessage> errors=new ArrayList<ErrorMessage>();

		Iterator<FieldError> iterator=objectErrors.iterator();		

		while(iterator.hasNext()){
			FieldError objectError=iterator.next();
			String code=objectError.getCode();

			if(code.equals("Size")||code.equals("NotBlank")){
				errors.add(new ErrorMessage(objectError.getField(),
						messages.getMessage(
								objectError.getCode()+"."+getDtoName(dto)+"."+objectError.getField(),
								null,locale)));
			}else{
				errors.add(new ErrorMessage(objectError.getField(),
						messages.getMessage(objectError.getCode(),
								null,locale)));				
			}			
		}

		dto.setErrors(errors);	
	}
}
