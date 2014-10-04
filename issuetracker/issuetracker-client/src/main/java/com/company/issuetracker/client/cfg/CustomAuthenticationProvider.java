package com.company.issuetracker.client.cfg;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.company.issuetracker.client.service.IssueTrackerConsumer;
import com.company.issuetracker.core.domain.User;
import com.company.issuetracker.core.domain.UserPK;
import com.company.issuetracker.core.dto.UserDTO;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IssueTrackerConsumer issueTracker;

	@Override
	public Authentication authenticate(final Authentication authentication)
			throws AuthenticationException {

		UserDTO userDTO=new UserDTO();
		userDTO.setUser(new User(new UserPK(authentication.getName())));
		userDTO.getUser().setPassword(authentication.getCredentials().toString());

		issueTracker.getAuthUser(userDTO);

		if(userDTO.getException()==null){
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

			Principal principal=new Principal() {

				@Override
				public String getName() {
					return authentication.getName();
				}
			}; 			

			return new UsernamePasswordAuthenticationToken(principal, authentication,
					grantedAuthorities);			
		}else{
			String message;

			if(userDTO.getException().equals("401 Unauthorized")){
				message="Incorrect credentials.";						
			}else{
				message="Provider error: "+userDTO.getException()+".";
			}

			throw new BadCredentialsException("Unable to sign in. "+message);
		}		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
