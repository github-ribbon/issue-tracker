package com.company.issuetracker.core.domain;

import java.io.Serializable;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Project implements Serializable{

	private static final long serialVersionUID = 1L;

	@Valid
	protected ProjectPK id;

	@Size(max=500)
	protected String description;

	protected User owner;

	protected Set<Version> versions;

	protected Set<Issue> issues;	

	public Project(){	
	}

	public Project(ProjectPK id){
		this.id=id;
	}

	public ProjectPK getId() {
		return id;
	}

	public void setId(ProjectPK id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Set<Version> getVersions() {
		return versions;
	}

	public void setVersions(Set<Version> versions) {
		this.versions = versions;
	}

	public Set<Issue> getIssues() {
		return issues;
	}

	public void setIssues(Set<Issue> issues) {
		this.issues = issues;
	}
}
