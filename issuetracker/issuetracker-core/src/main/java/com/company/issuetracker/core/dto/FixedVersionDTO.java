package com.company.issuetracker.core.dto;

import javax.validation.Valid;

import com.company.issuetracker.core.domain.FixedVersion;

public class FixedVersionDTO extends AbstractDTO{

	private static final long serialVersionUID = 1L;

	@Valid
	private FixedVersion fixedVersion;

	public FixedVersion getFixedVersion() {
		return fixedVersion;
	}

	public void setFixedVersion(FixedVersion fixedVersion) {
		this.fixedVersion = fixedVersion;
	}
}
