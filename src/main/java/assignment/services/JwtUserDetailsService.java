package assignment.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import assignment.entity.User;
import assignment.model.UserDto;
import assignment.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userDao.findByUsername(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
				new ArrayList<>());
	}
	

	public void save(UserDto userDto) {
		   Optional<User> existingUser = userDao.findByUsername(userDto.getUsername());
	        if (existingUser.isPresent()) {
	            throw new IllegalArgumentException("Username already exists");
	        }

		  User user = new User();
	      user.setUsername(userDto.getUsername());
	      user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
	      userDao.save(user);
	}

}