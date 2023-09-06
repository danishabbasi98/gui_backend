package com.vodacom.er.gui.service;

import com.vodacom.er.gui.entity.Users;
import com.vodacom.er.gui.repository.EsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MyUserDetails implements UserDetails {

    @Autowired
    private EsUserRepository userRepository;

    private Users user;
    String ROLE_PREFIX = "ROLE_";

    public MyUserDetails() {
    }

    public MyUserDetails(Users user) {
        this.user = user;
    }


    public String getRole(String username) {
        Users user1 = userRepository.findByUserName(username);
        if(user1 != null){
            return user1.getRole();
        }
        return null;
    }

    public Users getUser(String username) {
        Users user1 = userRepository.findByUserName(username);
        return user1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().toUpperCase()));
        return list;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUser_name();
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