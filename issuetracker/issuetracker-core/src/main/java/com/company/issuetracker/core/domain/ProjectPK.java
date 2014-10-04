package com.company.issuetracker.core.domain;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ProjectPK implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String ownerId;

	@Size(min=2,max=20)
	@NotBlank
	protected String projectId;

	public ProjectPK(){
	}

	public ProjectPK(String ownerId, String projectId) {
		this.ownerId = ownerId;
		this.projectId = projectId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
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
		if (!(obj instanceof ProjectPK)) {
			return false;
		}
		ProjectPK other = (ProjectPK) obj;
		if (ownerId == null) {
			if (other.ownerId != null) {
				return false;
			}
		} else if (!ownerId.equals(other.ownerId)) {
			return false;
		}
		if (projectId == null) {
			if (other.projectId != null) {
				return false;
			}
		} else if (!projectId.equals(other.projectId)) {
			return false;
		}
		return true;
	}	
}
