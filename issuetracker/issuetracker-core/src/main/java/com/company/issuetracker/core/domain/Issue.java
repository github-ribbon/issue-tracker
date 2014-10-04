package com.company.issuetracker.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Issue implements Serializable {

	private static final long serialVersionUID = 1L;

	protected long issueId;	

	protected String ownerId;

	protected String projectId;	

	protected String reporter;

	protected String assignee;

	protected String statusId;

	protected String priorityId;

	protected String issueTypeId;

	@Size(min=2, max=50)
	@NotBlank
	protected String title;

	@Size(min=2, max=500)
	@NotBlank
	protected String content; 

	protected Date created;

	protected Date modified;

	protected Set<FixedVersion> fixedVersions;

	protected Set<AffectedVersion> affectedVersions;

	protected Set<Response> responses;

	protected Project project;

	protected User assigneeUser;

	protected User reporterUser;

	protected Priority priority;

	protected Status status;

	protected IssueType issueType;

	public Issue(){
	}

	public Issue(long issueId){
		this.issueId=issueId;
	}


	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}

	public String getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(String issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Set<FixedVersion> getFixedVersions() {
		return fixedVersions;
	}

	public void setFixedVersions(Set<FixedVersion> fixedVersions) {
		this.fixedVersions = fixedVersions;
	}

	public Set<AffectedVersion> getAffectedVersions() {
		return affectedVersions;
	}

	public void setAffectedVersions(Set<AffectedVersion> affectedVersions) {
		this.affectedVersions = affectedVersions;
	}

	public Set<Response> getResponses() {
		return responses;
	}

	public void setResponses(Set<Response> responses) {
		this.responses = responses;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getAssigneeUser() {
		return assigneeUser;
	}

	public void setAssigneeUser(User assigneeUser) {
		this.assigneeUser = assigneeUser;
	}

	public User getReporterUser() {
		return reporterUser;
	}

	public void setReporterUser(User reporterUser) {
		this.reporterUser = reporterUser;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
