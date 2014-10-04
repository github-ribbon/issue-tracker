package com.company.issuetracker.provider.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IssueTypePKEntity implements Serializable{

	private static final long serialVersionUID = 1L;	

	@Column(name = "issue_type_id", length = 1)
	private String issueTypeId;

	public IssueTypePKEntity(){
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
		if (!(obj instanceof IssueTypePKEntity)) {
			return false;
		}
		IssueTypePKEntity other = (IssueTypePKEntity) obj;
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
