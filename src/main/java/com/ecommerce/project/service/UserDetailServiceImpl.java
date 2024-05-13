package com.ecommerce.project.service;

import com.ecommerce.project.entity.Users;
import com.ecommerce.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findById(username)
                .orElseThrow(()-> new UsernameNotFoundException("username" +username+ "not found!" ));
        return UserDetailImpl.build(users);
    }
}
