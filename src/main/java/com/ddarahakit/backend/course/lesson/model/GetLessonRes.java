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
public class GetLessonRes {
    private Integer idx;
    private Integer num;
    private String title;
    private Time time;
    private String detail;
}
