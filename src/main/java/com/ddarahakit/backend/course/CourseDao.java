package com.ddarahakit.backend.course;

import com.ddarahakit.backend.course.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CourseDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean isPreExistsCourse(PostCourseReq postCourseReq) {
        String checkPreExistCourseQuery = "select exists(select name from course where name = ?)";

        Integer existsNum = this.jdbcTemplate.queryForObject(checkPreExistCourseQuery, Integer.class, postCourseReq.getName());

        return existsNum == 1;
    }


    public boolean isNotExistedCourse(Integer idx) {
        String checkNullQuery = "select count(case when idx = ? then 1 end) as rowN from course";

        Integer isExistedNum = this.jdbcTemplate.queryForObject(checkNullQuery, Integer.class, idx);
        return (isExistedNum.equals(0));
    }

    public PostCourseRes createCourse(PostCourseReq postCourseReq) {
        String createCourseQuery = "insert into course (name, price, description, detail, discount, category_idx) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Object[] createCourseParams = new Object[] {
                postCourseReq.getName(),
                postCourseReq.getPrice(),
                postCourseReq.getDescription(),
                postCourseReq.getDetail(),
                postCourseReq.getDiscount(),
                postCourseReq.getCategory_idx()
        };

        this.jdbcTemplate.update(createCourseQuery, createCourseParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);

        return new PostCourseRes(lastInsertIdx, 1);
    }

    public PostCourseRes createCourseImage(String imageUrl, Integer course_idx) {
        String createCourseQuery = "insert into courseimage (imageurl, course_idx) VALUES (?, ?)";
        Object[] createCourseParams = new Object[] { imageUrl, course_idx};

        this.jdbcTemplate.update(createCourseQuery, createCourseParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);

        return new PostCourseRes(lastInsertIdx, 1);
    }


    public PostCourseRes createCourseDetailImage(String imageUrl, Integer course_idx) {
        String createCourseDetailQuery = "insert into coursedetailimage (imageurl, course_idx) VALUES (?, ?)";
        Object[] createCourseDetailParams = new Object[] { imageUrl, course_idx};

        this.jdbcTemplate.update(createCourseDetailQuery, createCourseDetailParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);

        return new PostCourseRes(lastInsertIdx, 1);
    }


    public GetCourseWithImageRes getCourseWithImage(Integer idx) {
        String getCourseQuery = "select * from course " +
                "left outer join courseimage on course.idx=courseimage.course_idx " +
                "left outer join coursedetailimage on course.idx=coursedetailimage.course_idx " +
                "where course.idx = ?";


        return this.jdbcTemplate.queryForObject(getCourseQuery
                , (rs,rowNum) -> new GetCourseWithImageRes(
                        rs.getInt("idx"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("description"),
                        rs.getString("detail"),
                        rs.getInt("discount"),
                        rs.getInt("category_idx"),
                        rs.getString(11),
                        rs.getString(16),
                        rs.getTimestamp("create_timestamp"),
                        rs.getTimestamp("update_timestamp")), idx);
    }



    public List<GetCourseWithImageRes> getCourseWithImageList() {

        String getCourseQuery = "select * from course " +
                "left outer join courseimage on course.idx=courseimage.course_idx " +
                "left outer join coursedetailimage on course.idx=coursedetailimage.course_idx";

        return this.jdbcTemplate.query(getCourseQuery
                , (rs,rowNum) -> new GetCourseWithImageRes(
                        rs.getInt("idx"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("description"),
                        rs.getString("detail"),
                        rs.getInt("discount"),
                        rs.getInt("category_idx"),
                        rs.getString(11),
                        rs.getString(16),
                        rs.getTimestamp("create_timestamp"),
                        rs.getTimestamp("update_timestamp")));
    }
}
