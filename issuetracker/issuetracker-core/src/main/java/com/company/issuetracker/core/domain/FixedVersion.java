package com.company.issuetracker.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class FixedVersion implements Serializable {

	private static final long serialVersionUID = 1L;

	protected FixedVersionPK id;

	protected Issue issue;

	protected Version version;

	public FixedVersion(){
	}

	public FixedVersionPK getId() {
		return id;
	}

	public void setId(FixedVersionPK id) {
		this.id = id;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}	
}
