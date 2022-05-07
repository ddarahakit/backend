package com.ddarahakit.backend.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostOrderRes {
    private Integer idx;
    private Integer status;
}