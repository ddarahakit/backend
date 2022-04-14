package com.ddarahakit.backend.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class GetCourseRes {
    private Integer idx;
    private String name;
    private Integer price;
    private String description;
    private Integer discount;
    private Integer category_idx;
    private Timestamp create_timestamp;
    private Timestamp update_timestamp;
}