package com.company.issuetracker.core.dto;

import javax.validation.Valid;

import com.company.issuetracker.core.domain.AffectedVersion;

public class AffectedVersionDTO extends AbstractDTO {

	private static final long serialVersionUID = 1L;

	@Valid
	private AffectedVersion affectedVersion;

	public AffectedVersion getAffectedVersion() {
		return affectedVersion;
	}

	public void setAffectedVersion(AffectedVersion affectedVersion) {
		this.affectedVersion = affectedVersion;
	}
}
