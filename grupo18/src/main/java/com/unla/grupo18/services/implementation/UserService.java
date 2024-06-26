package com.unla.grupo18.services.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unla.grupo18.entities.UserRole;
import com.unla.grupo18.repositories.IUserRepository;

@Service("userService")
public class UserService implements UserDetailsService {

	private IUserRepository userRepository;

	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.unla.grupo18.entities.User user = userRepository.findByUsername(username);
		 if (user == null) {
	            throw new UsernameNotFoundException("User not found: " + username);
	        }

		return buildUser(user, buildGrantedAuthorities(user.getUserRole()));
	}

	private User buildUser(com.unla.grupo18.entities.User user, List<GrantedAuthority> grantedAuthorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
						true, true, true, //accountNonExpired, credentialsNonExpired, accountNonLocked,
						grantedAuthorities);
	}

	private List<GrantedAuthority> buildGrantedAuthorities(UserRole userRole) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
			grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
		
		return new ArrayList<>(grantedAuthorities);
	}
	
}
