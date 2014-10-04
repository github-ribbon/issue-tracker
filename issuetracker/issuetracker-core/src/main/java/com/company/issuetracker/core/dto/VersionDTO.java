package com.company.issuetracker.core.dto;

import javax.validation.Valid;

import com.company.issuetracker.core.domain.Version;

public class VersionDTO extends AbstractDTO{

	private static final long serialVersionUID = 1L;

	@Valid
	private Version version;

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	} 	
}
