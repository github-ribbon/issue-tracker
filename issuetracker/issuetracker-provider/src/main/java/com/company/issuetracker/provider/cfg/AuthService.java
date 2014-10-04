package com.company.issuetracker.provider.cfg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.company.issuetracker.provider.domain.UserEntity;
import com.company.issuetracker.provider.domain.UserPKEntity;

public class AuthService implements UserDetailsService {

	@Autowired
	private UserEntity userEntity;

	public AuthService(){

	}

	@Override
	public UserDetails loadUserByUsername(String username){

		try {

			UserPKEntity usrPK=new UserPKEntity();
			usrPK.setUserId(username);

			UserEntity domainUser = userEntity.get(usrPK);

			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));				

			return new org.springframework.security.core.userdetails.User(
					domainUser.getId().getUserId(), 
					domainUser.getPassword(),
					true,
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					grantedAuthorities);

		}catch (Exception e) {
			e.printStackTrace();
			return new org.springframework.security.core.userdetails.User(null, null, null);
		}
	}
}
