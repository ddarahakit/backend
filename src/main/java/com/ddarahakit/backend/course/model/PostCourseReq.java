package com.ddarahakit.backend.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PostCourseReq {
    private String name;
    private Integer price;
    private String description;
    private Integer discount;
    private Integer category_idx;
}