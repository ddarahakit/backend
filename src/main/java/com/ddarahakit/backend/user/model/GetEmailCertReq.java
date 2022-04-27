package com.ddarahakit.backend.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class GetEmailCertReq {
    private String token;
    private String email;
}
