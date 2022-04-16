package com.ddarahakit.backend.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class GetCourseWithImageRes {
    private Integer idx;
    private String name;
    private Integer price;
    private String description;
    private String detail;
    private Integer discount;
    private Integer category_idx;
    private String course_imageurl;
    private String coursedetail_imageurl;
    private Timestamp create_timestamp;
    private Timestamp update_timestamp;
}