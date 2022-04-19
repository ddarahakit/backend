package com.ddarahakit.backend.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostSignupReq {
    private String email;
    private String nickname;
    private String password;
}
