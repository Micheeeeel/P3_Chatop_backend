package com.chatop.backend.service;

import com.chatop.backend.model.DAOUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collections;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private DAOUser user;

    // constructor
    public CustomUserDetails(DAOUser user) {
        this.user = user;
    }

    // Getters and setters for email
    public String getEmail() {
        return user.getEmail();
    }

    public void setEmail(String email) {
        user.setEmail(email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // In case you have roles or authorities assigned to the user, return them here.
        // In the simplest case, you can return an empty list
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Add logic here, for simplicity return true
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Add logic here, for simplicity return true
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Add logic here, for simplicity return true
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Add logic here, for simplicity return true
        return true;
    }
}
