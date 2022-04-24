package com.ddarahakit.backend.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class LoginUser extends User {
    String email;
    String nickname;

    public LoginUser(String username, String password, String nickname, Collection<?
            extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.nickname = nickname;
    }
}