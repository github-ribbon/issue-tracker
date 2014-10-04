package com.company.issuetracker.provider.validation;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.company.issuetracker.core.dto.ProjectDTO;
import com.company.issuetracker.provider.domain.ProjectEntity;
import com.company.issuetracker.provider.domain.ProjectPKEntity;

@Component
public class ProjectDTOValidator{

	@Autowired
	private ProjectEntity projectEntity;

	@Autowired
	private DozerBeanMapper mapper;

	public void validate(ProjectDTO projectDTO, Errors errors) {

		if(projectEntity.exists(mapper.map(projectDTO.getProject().getId(), ProjectPKEntity.class))){
			errors.rejectValue("project.id.projectId", "error.projectExists");
		}
	}
}

