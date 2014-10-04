package com.company.issuetracker.core.domain;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Priority implements Serializable {

	private static final long serialVersionUID = 1L;

	protected PriorityPK id;

	protected Set<Issue> issues;

	public Priority(){
	}

	public PriorityPK getId() {
		return id;
	}

	public void setId(PriorityPK id) {
		this.id = id;
	}

	public Set<Issue> getIssues() {
		return issues;
	}

	public void setIssues(Set<Issue> issues) {
		this.issues = issues;
	}	
}
