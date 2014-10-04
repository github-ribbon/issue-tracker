package com.company.issuetracker.core.domain;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class IssueType implements Serializable {

	private static final long serialVersionUID = 1L;

	protected IssueTypePK id;	

	protected Set<Issue> issues;

	public IssueType(){
	}

	public IssueTypePK getId() {
		return id;
	}

	public void setId(IssueTypePK id) {
		this.id = id;
	}

	public Set<Issue> getIssues() {
		return issues;
	}

	public void setIssues(Set<Issue> issues) {
		this.issues = issues;
	}	
}
