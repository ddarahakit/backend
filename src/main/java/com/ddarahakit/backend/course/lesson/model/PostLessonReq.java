package com.ddarahakit.backend.course.lesson.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PostLessonReq {
    private Integer num;
    private String title;
    private Time time;
    private String detail;
    private Integer chapter_idx;
}
