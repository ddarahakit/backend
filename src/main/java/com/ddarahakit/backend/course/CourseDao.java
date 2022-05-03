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


    public GetCourseWithAll getCourseWithAll(Integer idx) {
        String getCourseQuery = "SELECT course.idx, course.name, course.price, course.description, course.detail, course.discount, category.title, chapter_lesson, chapter_time, courseimage.imageurl\n" +
                "FROM (SELECT course.idx, course.name, course.price, course.description, course.detail, course.discount, course.category_idx, GROUP_CONCAT(ch_name ORDER BY chapter_lesson.num SEPARATOR '^') AS chapter_lesson, GROUP_CONCAT(le_time ORDER BY chapter_lesson.num SEPARATOR '^') AS chapter_time FROM (SELECT CONCAT_WS(':', name, le_title) as ch_name, le_time, chapter.num, chapter.course_idx FROM\n" +
                "(SELECT * FROM (SELECT lesson.chapter_idx, GROUP_CONCAT(title ORDER BY lesson.num) as le_title, GROUP_CONCAT(time ORDER BY lesson.num) as le_time FROM lesson\n" +
                "GROUP BY lesson.chapter_idx) as lesson JOIN chapter ON lesson.chapter_idx=chapter.idx) as chapter) as chapter_lesson\n" +
                "JOIN course ON chapter_lesson.course_idx=course.idx) AS course \n" +
                "LEFT OUTER JOIN category ON category.idx=course.category_idx\n" +
                "LEFT OUTER JOIN courseimage ON courseimage.course_idx=course.idx WHERE course.idx=?";


        return this.jdbcTemplate.queryForObject(getCourseQuery
                , (rs,rowNum) -> new GetCourseWithAll(
                        rs.getInt("idx"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("description"),
                        rs.getString("detail"),
                        rs.getInt("discount"),
                        rs.getString("title"),
                        rs.getString("chapter_lesson"),
                        rs.getString("chapter_time"),
                        rs.getString("imageurl")), idx);
    }



    public List<GetCourseWithAll> getCourseWithAllList() {

        String getCourseQuery = "SELECT course.idx, course.name, course.price, course.description, course.detail, course.discount, category.title, chapter_lesson, chapter_time, courseimage.imageurl\n" +
                "FROM (SELECT course.idx, course.name, course.price, course.description, course.detail, course.discount, course.category_idx, GROUP_CONCAT(ch_name ORDER BY chapter_lesson.num SEPARATOR '^') AS chapter_lesson, GROUP_CONCAT(le_time ORDER BY chapter_lesson.num SEPARATOR '^') AS chapter_time FROM (SELECT CONCAT_WS(':', name, le_title) as ch_name, le_time, chapter.num, chapter.course_idx FROM\n" +
                "(SELECT * FROM (SELECT lesson.chapter_idx, GROUP_CONCAT(title ORDER BY lesson.num) as le_title, GROUP_CONCAT(time ORDER BY lesson.num) as le_time FROM lesson\n" +
                "GROUP BY lesson.chapter_idx) as lesson JOIN chapter ON lesson.chapter_idx=chapter.idx) as chapter) as chapter_lesson\n" +
                "JOIN course ON chapter_lesson.course_idx=course.idx) AS course \n" +
                "LEFT OUTER JOIN category ON category.idx=course.category_idx\n" +
                "LEFT OUTER JOIN courseimage ON courseimage.course_idx=course.idx";

        return this.jdbcTemplate.query(getCourseQuery
                , (rs,rowNum) -> new GetCourseWithAll(
                        rs.getInt("idx"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("description"),
                        rs.getString("detail"),
                        rs.getInt("discount"),
                        rs.getString("title"),
                        rs.getString("chapter_lesson"),
                        rs.getString("chapter_time"),
                        rs.getString("imageurl")));
    }
}
