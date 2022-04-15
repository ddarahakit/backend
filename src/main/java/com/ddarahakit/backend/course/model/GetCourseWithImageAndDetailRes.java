package com.ddarahakit.backend.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class GetCourseWithImageAndDetailRes {
    private Integer idx;
    private String name;
    private Integer price;
    private String description;
    private Integer discount;
    private Integer category_idx;
    private String course_imageurl;
    private String detail;
    private String coursedetail_imageurl;
    private Timestamp create_timestamp;
    private Timestamp update_timestamp;
}