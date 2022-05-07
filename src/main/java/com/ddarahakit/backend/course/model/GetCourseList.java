package com.ddarahakit.backend.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class GetCourseList {
    private Integer idx;
    private String name;
    private Integer price;
    private String description;
    private Integer discount;
    private String imageurl;
}