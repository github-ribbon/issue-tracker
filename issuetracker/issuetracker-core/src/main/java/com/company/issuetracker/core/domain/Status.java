package com.company.issuetracker.core.domain;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Status implements Serializable {

	private static final long serialVersionUID = 1L;

	protected StatusPK id;	

	protected Set<Issue> issues;

	public Status(){
	}

	public StatusPK getId() {
		return id;
	}

	public void setId(StatusPK id) {
		this.id = id;
	}

	public Set<Issue> getIssues() {
		return issues;
	}

	public void setIssues(Set<Issue> issues) {
		this.issues = issues;
	}	
}
