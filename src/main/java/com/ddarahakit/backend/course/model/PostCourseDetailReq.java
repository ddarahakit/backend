package com.ddarahakit.backend.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PostCourseDetailReq {
    private String detail;
    private Integer course_idx;
}