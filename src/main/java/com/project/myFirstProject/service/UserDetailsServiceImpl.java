package com.project.myFirstProject.service;

import com.project.myFirstProject.entity.User;
import com.project.myFirstProject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User byUsername = userRepo.findByUsername(username);
        if (byUsername!=null)
        {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(byUsername.getUsername())
                    .password(byUsername.getPassword())
                    .roles(byUsername.getRoles().toArray(new String[0]))
                    .build();
        }
        throw  new UsernameNotFoundException("User not found exception" + username);
    }
}
