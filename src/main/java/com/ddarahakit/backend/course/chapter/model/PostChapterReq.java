package com.ddarahakit.backend.course.chapter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PostChapterReq {
    private Integer num;
    private String name;
    private Integer course_idx;
}