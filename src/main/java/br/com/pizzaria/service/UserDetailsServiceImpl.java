package br.com.pizzaria.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pizzaria.domain.SystemUser;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private SystemUserService service;
	
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		SystemUser user = service.getSystemUser(email);
		UserDetails userDetails = null;

		if(user != null) {
			List<GrantedAuthority> grantedAuthorityList = getGrantedAuthorityList(user);
			userDetails = new User(user.getEmail(),
					new BCryptPasswordEncoder().encode(user.getPassword()),
					grantedAuthorityList);
		}
		
		return userDetails;
	}
	
	private List<GrantedAuthority> getGrantedAuthorityList(SystemUser user){
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
		
		grantedAuthorityList.add(new SimpleGrantedAuthority(user.getRole().getName()));
		
		return grantedAuthorityList;
	}

}
