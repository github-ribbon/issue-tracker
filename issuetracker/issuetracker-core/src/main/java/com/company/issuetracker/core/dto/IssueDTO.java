package com.company.issuetracker.core.dto;

import javax.validation.Valid;

import com.company.issuetracker.core.domain.Issue;
import com.company.issuetracker.core.domain.Version;
import com.company.issuetracker.core.util.Wrapper;

public class IssueDTO extends AbstractDTO {

	private static final long serialVersionUID = 1L;

	@Valid 
	private Issue issue;

	private Long issueId;

	private Wrapper<Issue> issuesWrapper;

	private Wrapper<Version> notFixedVersionsWrapper;

	private Wrapper<Version> notAffectedVersionsWrapper;

	private Pager pager;

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Wrapper<Version> getNotFixedVersionsWrapper() {
		return notFixedVersionsWrapper;
	}

	public void setNotFixedVersionsWrapper(Wrapper<Version> notFixedVersionsWrapper) {
		this.notFixedVersionsWrapper = notFixedVersionsWrapper;
	}

	public Wrapper<Version> getNotAffectedVersionsWrapper() {
		return notAffectedVersionsWrapper;
	}

	public void setNotAffectedVersionsWrapper(
			Wrapper<Version> notAffectedVersionsWrapper) {
		this.notAffectedVersionsWrapper = notAffectedVersionsWrapper;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public Wrapper<Issue> getIssuesWrapper() {
		return issuesWrapper;
	}

	public void setIssuesWrapper(Wrapper<Issue> issuesWrapper) {
		this.issuesWrapper = issuesWrapper;
	}
}
