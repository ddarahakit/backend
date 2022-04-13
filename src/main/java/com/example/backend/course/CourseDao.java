package com.example.backend.course;

import com.example.backend.course.model.GetCourseRes;
import com.example.backend.course.model.PostCourseReq;
import com.example.backend.course.model.PostCourseRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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
        Object[] createCourseParams = new Object[] { postCourseReq.getName(), postCourseReq.getPrice(), postCourseReq.getDescription(), postCourseReq.getDetail(),
                postCourseReq.getDiscount(), postCourseReq.getCategory_idx()};

        this.jdbcTemplate.update(createCourseQuery, createCourseParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);

        return new PostCourseRes(lastInsertIdx, 1);
    }

    public GetCourseRes getCourse(Integer idx) {
        String getCourseQuery = "select * from Course where idx = ?";

        return this.jdbcTemplate.queryForObject(getCourseQuery
                , (rs,rowNum) -> new GetCourseRes(
                        rs.getInt("idx"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("description"),
                        rs.getString("detail"),
                        rs.getInt("discount"),
                        rs.getInt("category_idx"),
                        rs.getTimestamp("create_timestamp"),
                        rs.getTimestamp("update_timestamp")), idx);
    }
}
