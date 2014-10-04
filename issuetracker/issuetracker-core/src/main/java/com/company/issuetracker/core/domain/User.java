package com.company.issuetracker.core.domain;

import java.io.Serializable;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Valid
	protected UserPK id;

	@Size(min=2,max=50)
	@NotBlank
	protected String name;

	@Size(min=2,max=50)
	@NotBlank
	protected String surname;

	@Size(min=6,max=200)
	@NotBlank
	protected String password;

	protected Set<Project> projects;

	protected Set<Response> responses;

	protected Set<Issue> assigneeIssues;

	protected Set<Issue> reporterIssues;

	public User(){
	}

	public User(UserPK id){
		this.id=id;
	}

	public UserPK getId() {
		return id;
	}

	public void setId(UserPK id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<Response> getResponses() {
		return responses;
	}

	public void setResponses(Set<Response> response) {
		this.responses = response;
	}

	public Set<Issue> getAssigneeIssues() {
		return assigneeIssues;
	}

	public void setAssigneeIssues(Set<Issue> assigneeIssues) {
		this.assigneeIssues = assigneeIssues;
	}

	public Set<Issue> getReporterIssues() {
		return reporterIssues;
	}

	public void setReporterIssues(Set<Issue> reporterIssues) {
		this.reporterIssues = reporterIssues;
	}
}
