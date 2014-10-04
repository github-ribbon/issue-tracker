package com.company.issuetracker.core.domain;

import java.io.Serializable;

public class StatusPK implements Serializable{

	private static final long serialVersionUID = 1L;	

	protected String statusId;

	public StatusPK(){
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
		if (!(obj instanceof StatusPK)) {
			return false;
		}
		StatusPK other = (StatusPK) obj;
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