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


    public GetCourseDetail getCourseWithAll(Integer idx) {
        String getCourseQuery = "SELECT co_idx, co_name, co_price, co_description, co_detail, co_discount, ci_imageurl, ca_title, group_concat(ch_lesson order by ch_num separator '^') as ch_lesson, group_concat(ch_time order by ch_num separator '^') as ch_time FROM (SELECT *, concat(ch_name,'_',group_concat(lesson.title)) as ch_lesson, group_concat(lesson.time) as ch_time FROM (SELECT co_idx, co_name, co_price, co_description, co_detail, co_discount, ci_imageurl, ca_title, ch_name, ch_num, lesson.title, lesson.time,lesson.chapter_idx FROM (SELECT course.idx as co_idx, course.name as co_name, course.price as co_price, course.description as co_description, course.detail as co_detail, course.discount co_discount, courseimage.imageurl ci_imageurl, chapter.idx as ch_idx, chapter.name as ch_name, chapter.num as ch_num, category.title as ca_title FROM course LEFT OUTER JOIN chapter ON course.idx=chapter.course_idx LEFT OUTER JOIN courseimage ON course.idx=courseimage.course_idx LEFT OUTER JOIN category ON course.category_idx=category.idx WHERE course.idx=?) AS chapter LEFT OUTER JOIN lesson on chapter.ch_idx=lesson.chapter_idx) as lesson GROUP BY lesson.chapter_idx) as chapter_lesson GROUP BY co_idx";


        return this.jdbcTemplate.queryForObject(getCourseQuery
                , (rs,rowNum) -> new GetCourseDetail(
                        rs.getInt("co_idx"),
                        rs.getString("co_name"),
                        rs.getInt("co_price"),
                        rs.getString("co_description"),
                        rs.getString("co_detail"),
                        rs.getInt("co_discount"),
                        rs.getString("ca_title"),
                        rs.getString("ch_lesson"),
                        rs.getString("ch_time"),
                        rs.getString("ci_imageurl")), idx);
    }



    public List<GetCourseList> getCourseWithAllList() {

        String getCourseQuery = "SELECT * FROM course LEFT OUTER JOIN courseimage ON courseimage.course_idx=course.idx";

        return this.jdbcTemplate.query(getCourseQuery
                , (rs,rowNum) -> new GetCourseList(
                        rs.getInt("idx"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("description"),
                        rs.getInt("discount"),
                        rs.getString("imageurl")));
    }
}
