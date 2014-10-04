package com.company.issuetracker.provider.validation;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.company.issuetracker.core.dto.AffectedVersionDTO;
import com.company.issuetracker.provider.domain.AffectedVersionEntity;
import com.company.issuetracker.provider.domain.AffectedVersionPKEntity;
import com.company.issuetracker.provider.domain.IssueEntity;
import com.company.issuetracker.provider.domain.ProjectPKEntity;
import com.company.issuetracker.provider.domain.VersionEntity;
import com.company.issuetracker.provider.domain.VersionPKEntity;

@Component
public class AffectedVersionDTOValidator{

	@Autowired
	private AffectedVersionEntity affectedVersion;

	@Autowired
	private VersionEntity version;

	@Autowired
	private IssueEntity issue;

	@Autowired
	private DozerBeanMapper mapper;

	public void validate(AffectedVersionDTO affectedVersionDTO, Errors errors) {

		if(affectedVersion.exists(
				mapper.map(affectedVersionDTO.getAffectedVersion().getId(), AffectedVersionPKEntity.class))){
			errors.rejectValue("affectedVersion.id.versionId", "error.affectedVersionExists");
		}		

		if(!issue.exists(affectedVersionDTO.
				getAffectedVersion().getId().getIssueId())){				
			errors.rejectValue("affectedVersion.id.versionId", "error.issueNotMatched");
		}else{

			IssueEntity i=issue.get(affectedVersionDTO.
					getAffectedVersion().getId().getIssueId());

			if(!new ProjectPKEntity(i.getOwnerId(),i.getProjectId())
			.equals
			(new ProjectPKEntity(
					affectedVersionDTO.getAffectedVersion().getId().getOwnerId(),
					affectedVersionDTO.getAffectedVersion().getId().getProjectId())
					)){
				errors.rejectValue("affectedVersion.id.versionId", "error.projectNotMatched");
			}
		}

		if(!version.exists(new VersionPKEntity(affectedVersionDTO.
				getAffectedVersion().getId().getOwnerId(),
				affectedVersionDTO.getAffectedVersion().getId().getProjectId(),
				affectedVersionDTO.getAffectedVersion().getId().getVersionId()))){				
			errors.rejectValue("affectedVersion.id.versionId", "error.versionNotMatched");
		}
	}
}

