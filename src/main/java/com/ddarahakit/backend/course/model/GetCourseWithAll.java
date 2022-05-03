package com.ddarahakit.backend.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class GetCourseWithAll {
    private Integer idx;
    private String name;
    private Integer price;
    private String description;
    private String detail;
    private Integer discount;
    private String title;
    private String chapter_lesson;
    private String chapter_time;
    private String imageurl;
}