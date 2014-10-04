package com.company.issuetracker.provider.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StatusPKEntity implements Serializable{

	private static final long serialVersionUID = 1L;	

	@Column(name = "status_id", length = 1)
	private String statusId;

	public StatusPKEntity(){
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((statusId == null) ? 0 : statusId.hashCode());
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
		if (!(obj instanceof StatusPKEntity)) {
			return false;
		}
		StatusPKEntity other = (StatusPKEntity) obj;
		if (statusId == null) {
			if (other.statusId != null) {
				return false;
			}
		} else if (!statusId.equals(other.statusId)) {
			return false;
		}
		return true;
	}
}