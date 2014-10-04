package com.company.issuetracker.provider.validation;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.company.issuetracker.core.dto.VersionDTO;
import com.company.issuetracker.provider.domain.ProjectEntity;
import com.company.issuetracker.provider.domain.ProjectPKEntity;
import com.company.issuetracker.provider.domain.VersionEntity;
import com.company.issuetracker.provider.domain.VersionPKEntity;

@Component
public class VersionDTOValidator{

	@Autowired
	private DozerBeanMapper mapper;

	@Autowired
	private VersionEntity versionEntity;

	@Autowired
	private ProjectEntity projectEntity;

	public void validate(VersionDTO versionDTO, Errors errors) {

		if(versionEntity.exists(mapper.map(versionDTO.getVersion().getId(),VersionPKEntity.class))){
			errors.rejectValue("version.id.versionId", "error.versionExists");
		}		

		if(!projectEntity.exists(new ProjectPKEntity(versionDTO.getVersion().getId().getOwnerId(),
				versionDTO.getVersion().getId().getProjectId()))){
			errors.rejectValue("version.id.versionId", "error.projectNotMatched");
		}
	}
}

