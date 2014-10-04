package com.company.issuetracker.provider.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VersionPKEntity implements Serializable {

	private static final long serialVersionUID = 1L;	

	@Column(name = "owner_id", length = 20)
	private String ownerId;

	@Column(name = "project_id", length = 20)
	private String projectId;

	@Column(name = "version_id", length = 20)
	private String versionId;

	public VersionPKEntity(){
	}

	public VersionPKEntity(String ownerId, String projectId, String versionId) {
		this.ownerId = ownerId;
		this.projectId = projectId;
		this.versionId = versionId;
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

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result
				+ ((versionId == null) ? 0 : versionId.hashCode());
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
		if (!(obj instanceof VersionPKEntity)) {
			return false;
		}
		VersionPKEntity other = (VersionPKEntity) obj;
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
		if (versionId == null) {
			if (other.versionId != null) {
				return false;
			}
		} else if (!versionId.equals(other.versionId)) {
			return false;
		}
		return true;
	}	
}
