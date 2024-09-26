package com.ssk.SpringSecurityWithDaoAndJwt.service;

import com.ssk.SpringSecurityWithDaoAndJwt.dao.UserRepository;
import com.ssk.SpringSecurityWithDaoAndJwt.model.User;
import com.ssk.SpringSecurityWithDaoAndJwt.model.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user by username from the database
        User user = userRepository.findByUsername(username);

        // If user not found, throw exception
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Return an instance of UserDetailsImp which now contains the role as well
        return new UserDetailsImp(user);
    }
}
