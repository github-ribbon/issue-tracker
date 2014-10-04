package com.company.issuetracker.core.domain;

import java.io.Serializable;


public class PriorityPK implements Serializable{

	private static final long serialVersionUID = 1L;	

	protected String priorityId;

	public PriorityPK(){
	}

	public String getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((priorityId == null) ? 0 : priorityId.hashCode());
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
		if (!(obj instanceof PriorityPK)) {
			return false;
		}
		PriorityPK other = (PriorityPK) obj;
		if (priorityId == null) {
			if (other.priorityId != null) {
				return false;
			}
		} else if (!priorityId.equals(other.priorityId)) {
			return false;
		}
		return true;
	}	
}