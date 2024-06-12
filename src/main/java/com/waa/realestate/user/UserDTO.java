package com.waa.realestate.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String isActive;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDTO(Long id, String email, String username, String fullName, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
