package com.company.issuetracker.provider.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PriorityPKEntity implements Serializable{

	private static final long serialVersionUID = 1L;	

	@Column(name = "priority_id", length = 1)
	private String priorityId;

	public PriorityPKEntity(){
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
		if (!(obj instanceof PriorityPKEntity)) {
			return false;
		}
		PriorityPKEntity other = (PriorityPKEntity) obj;
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