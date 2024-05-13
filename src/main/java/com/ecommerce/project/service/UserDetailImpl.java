package com.ecommerce.project.service;

import com.ecommerce.project.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data

public class UserDetailImpl implements UserDetails {
    private String username;
    private String email;
    private String fullname;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String user_role;

    public UserDetailImpl(String username,
                          String email,
                          String fullname,
                          String password,
                          String user_role) {
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.user_role = user_role;
    }

    public static UserDetailImpl build (Users users){
        return new UserDetailImpl(users.getId(),
                                  users.getUsername(),
                                  users.getEmail(),
                                  users.getPassword(),
                                  users.getUser_role());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority>authorities = new ArrayList<>();
        if (StringUtils.hasText(user_role)){
            String[] splits = user_role.replaceAll(" ", "").split(",");
            for (String string : splits){
                authorities.add(new SimpleGrantedAuthority(string));
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
