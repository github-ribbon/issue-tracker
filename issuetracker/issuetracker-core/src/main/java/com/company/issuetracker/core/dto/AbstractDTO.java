package com.company.issuetracker.core.dto;

import java.io.Serializable;
import java.util.List;

import com.company.issuetracker.core.validation.ErrorMessage;

public abstract class AbstractDTO  implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ErrorMessage> errors;

	private String exception;

	public AbstractDTO(){
	}

	public List<ErrorMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}	
}
