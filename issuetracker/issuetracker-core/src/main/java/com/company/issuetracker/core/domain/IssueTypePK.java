package com.company.issuetracker.core.domain;

import java.io.Serializable;


public class IssueTypePK implements Serializable{

	private static final long serialVersionUID = 1L;	

	protected String issueTypeId;

	public IssueTypePK(){
	}

	public String getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(String issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((issueTypeId == null) ? 0 : issueTypeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IssueTypePK)) {
			return false;
		}
		IssueTypePK other = (IssueTypePK) obj;
		if (issueTypeId == null) {
			if (other.issueTypeId != null) {
				return false;
			}
		} else if (!issueTypeId.equals(other.issueTypeId)) {
			return false;
		}
		return true;
	}
}
