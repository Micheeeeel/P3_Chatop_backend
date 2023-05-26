
package com.chatop.backend.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// The Spring Security Authentication Manager calls this method for getting the user details from the database when authenticating the user details provided by the user.
// Here we are getting the user details from a hardcoded User List.
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("springuser".equals(username)) {
			//the password for a user is stored in encrypted format using BCrypt
			// using the Online Bcrypt Generator you can generate the Bcrypt for a password (https://www.javainuse.com/onlineBcrypt)
			return new User("springuser", "$2a$10$yZrmyJALdWHU0XKm4ypIOOq5zXhVPl.McM73xgIxdf5tMusSZGyVq",
					new ArrayList<>());
		} if ("springadmin".equals(username)) {
			
			return new User("springadmin", "$2a$10$6VhTvTWVPCANUdteldA1IeSSU8VlB4b4rLAA0XRMMpzft81MGjIJ2",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
