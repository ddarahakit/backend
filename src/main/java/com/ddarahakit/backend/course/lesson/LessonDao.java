package com.ddarahakit.backend.course.lesson;

import com.ddarahakit.backend.course.lesson.model.*;
import com.ddarahakit.backend.course.lesson.model.PostLessonReq;
import com.ddarahakit.backend.course.lesson.model.PostLessonRes;
import com.ddarahakit.backend.course.model.GetCourseDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class LessonDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean isPreExistsLesson(PostLessonReq postLessonReq) {
        String checkPreExistLessonQuery = "select exists(select name from lesson where title = ?)";

        Integer existsNum = this.jdbcTemplate.queryForObject(checkPreExistLessonQuery, Integer.class, postLessonReq.getTitle());

        return existsNum == 1;
    }


    public boolean isNotExistedLesson(Integer idx) {
        String checkNullQuery = "select count(case when idx = ? then 1 end) as rowN from lesson";

        Integer isExistedNum = this.jdbcTemplate.queryForObject(checkNullQuery, Integer.class, idx);
        return (isExistedNum.equals(0));
    }

    public PostLessonRes createLesson(PostLessonReq postLessonReq) {
        String createLessonQuery = "INSERT INTO lesson (num, title, time, detail, chapter_idx) VALUES (?, ?, ?, ?, ?)";
        Object[] createLessonParams = new Object[] {
                postLessonReq.getNum(),
                postLessonReq.getTitle(),
                postLessonReq.getTime(),
                postLessonReq.getDetail(),
                postLessonReq.getChapter_idx()};

        this.jdbcTemplate.update(createLessonQuery, createLessonParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);

        return new PostLessonRes(lastInsertIdx, 1);
    }


    public GetLessonRes getLessonByIdx(Integer idx) {
        String getLessonQuery = "SELECT * FROM lesson WHERE idx=?";

        return this.jdbcTemplate.queryForObject(getLessonQuery
                , (rs,rowNum) -> new GetLessonRes(
                        rs.getInt("idx"),
                        rs.getInt("num"),
                        rs.getString("title"),
                        rs.getTime("time"),
                        rs.getString("detail")), idx);
    }
}
