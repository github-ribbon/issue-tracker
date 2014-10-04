package com.company.issuetracker.core.dto;

import javax.validation.Valid;

import com.company.issuetracker.core.domain.User;
import com.company.issuetracker.core.util.Wrapper;

public class UserDTO extends AbstractDTO {

	private static final long serialVersionUID = 1L;

	@Valid
	private User user;

	private Boolean isAuth;

	private Wrapper<User> usersWrapper;	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Wrapper<User> getUsersWrapper() {
		return usersWrapper;
	}

	public void setUsersWrapper(Wrapper<User> usersWrapper) {
		this.usersWrapper = usersWrapper;
	}

	public Boolean getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Boolean isAuth) {
		this.isAuth = isAuth;
	}
}
