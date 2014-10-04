package com.company.issuetracker.core.domain;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class UserPK implements Serializable {

	private static final long serialVersionUID = 1L;	

	@Size(min=2,max=20)
	@NotBlank
	protected String userId;

	public UserPK(){
	}

	public UserPK(String userId){
		this.userId=userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		if (!(obj instanceof UserPK)) {
			return false;
		}
		UserPK other = (UserPK) obj;
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}
}
