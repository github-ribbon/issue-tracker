package com.company.issuetracker.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	protected long responseId;	

	protected long issueId;

	protected String userId;

	@Size(min=2,max=500)
	protected String content;	

	protected Date created;

	protected Date modified;

	protected Issue issue;

	protected User user;

	public Response(){
	}

	public long getResponseId() {
		return responseId;
	}

	public void setResponseId(long responseId) {
		this.responseId = responseId;
	}

	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
