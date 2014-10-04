package com.company.issuetracker.core.domain;

import java.io.Serializable;
import java.util.Set;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Version implements Serializable{

	private static final long serialVersionUID = 1L;

	@Valid
	protected VersionPK id;

	protected Project project;

	protected Set<AffectedVersion> affectedVersions;

	protected Set<FixedVersion> fixedVersions;

	public Version(){	
	}

	public Version(VersionPK id){
		this.id=id;
	}

	public VersionPK getId() {
		return id;
	}

	public void setId(VersionPK id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<AffectedVersion> getAffectedVersions() {
		return affectedVersions;
	}

	public void setAffectedVersions(Set<AffectedVersion> affectedVersions) {
		this.affectedVersions = affectedVersions;
	}

	public Set<FixedVersion> getFixedVersions() {
		return fixedVersions;
	}

	public void setFixedVersions(Set<FixedVersion> fixedVersions) {
		this.fixedVersions = fixedVersions;
	}
}
