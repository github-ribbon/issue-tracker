package com.company.issuetracker.core.dto;

import javax.validation.Valid;

import com.company.issuetracker.core.domain.Project;
import com.company.issuetracker.core.util.Wrapper;

public class ProjectDTO extends AbstractDTO {

	private static final long serialVersionUID = 1L;

	@Valid
	private Project project;	

	private Wrapper<Project> projectsWrapper;	

	private Pager pager;

	private Long numOfIssues;

	private String ownerId;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}	

	public Long getNumOfIssues() {
		return numOfIssues;
	}

	public void setNumOfIssues(Long numOfIssues) {
		this.numOfIssues = numOfIssues;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Wrapper<Project> getProjectsWrapper() {
		return projectsWrapper;
	}

	public void setProjectsWrapper(Wrapper<Project> projectsWrapper) {
		this.projectsWrapper = projectsWrapper;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
}
