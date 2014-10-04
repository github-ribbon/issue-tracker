package com.company.issuetracker.provider.validation;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.company.issuetracker.core.dto.FixedVersionDTO;
import com.company.issuetracker.provider.domain.FixedVersionEntity;
import com.company.issuetracker.provider.domain.FixedVersionPKEntity;
import com.company.issuetracker.provider.domain.IssueEntity;
import com.company.issuetracker.provider.domain.ProjectPKEntity;
import com.company.issuetracker.provider.domain.VersionEntity;
import com.company.issuetracker.provider.domain.VersionPKEntity;

@Component
public class FixedVersionDTOValidator{

	@Autowired
	private FixedVersionEntity fixedVersionEntity;

	@Autowired
	private VersionEntity versionEntity;

	@Autowired
	private IssueEntity issueEntity;

	@Autowired
	private DozerBeanMapper mapper;


	public void validate(FixedVersionDTO fixedVersionDTO, Errors errors) {

		if(fixedVersionEntity.exists(
				mapper.map(fixedVersionDTO.getFixedVersion().getId(), FixedVersionPKEntity.class))){
			errors.rejectValue("fixedVersion.id.versionId", "error.fixedVersionExists");
		}		

		if(!issueEntity.exists(fixedVersionDTO.
				getFixedVersion().getId().getIssueId())){				
			errors.rejectValue("fixedVersion.id.versionId", "error.issueNotMatched");
		}else{

			IssueEntity i=issueEntity.get(fixedVersionDTO.getFixedVersion().getId().getIssueId());

			if(!new ProjectPKEntity(i.getOwnerId(),i.getProjectId())
			.equals
			(new ProjectPKEntity(
					fixedVersionDTO.getFixedVersion().getId().getOwnerId(),
					fixedVersionDTO.getFixedVersion().getId().getProjectId())
					)){
				errors.rejectValue("fixedVersion.id.versionId", "error.projectNotMatched");
			}
		}

		if(!versionEntity.exists(new VersionPKEntity(fixedVersionDTO.
				getFixedVersion().getId().getOwnerId(),
				fixedVersionDTO.getFixedVersion().getId().getProjectId(),
				fixedVersionDTO.getFixedVersion().getId().getVersionId()))){				
			errors.rejectValue("fixedVersion.id.versionId", "error.versionNotMatched");
		}
	}
}

