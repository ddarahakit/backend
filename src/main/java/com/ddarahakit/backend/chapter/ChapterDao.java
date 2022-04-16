package com.ddarahakit.backend.chapter;

import com.ddarahakit.backend.chapter.model.PostChapterReq;
import com.ddarahakit.backend.chapter.model.PostChapterRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ChapterDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean isPreExistsChapter(PostChapterReq postChapterReq) {
        String checkPreExistChapterQuery = "select exists(select name from chapter where name = ?)";

        Integer existsNum = this.jdbcTemplate.queryForObject(checkPreExistChapterQuery, Integer.class, postChapterReq.getName());

        return existsNum == 1;
    }


    public boolean isNotExistedChapter(Integer idx) {
        String checkNullQuery = "select count(case when idx = ? then 1 end) as rowN from chapter";

        Integer isExistedNum = this.jdbcTemplate.queryForObject(checkNullQuery, Integer.class, idx);
        return (isExistedNum.equals(0));
    }

    public PostChapterRes createChapter(PostChapterReq postChapterReq) {
        String createChapterQuery = "insert into chapter (num, name, time, detail, course_idx) " +
                "VALUES (?, ?, ?, ?, ?)";
        Object[] createChapterParams = new Object[] {
                postChapterReq.getNum(),
                postChapterReq.getName(),
                postChapterReq.getTime(),
                postChapterReq.getDetail(),
                postChapterReq.getCourse_idx()};

        this.jdbcTemplate.update(createChapterQuery, createChapterParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);

        return new PostChapterRes(lastInsertIdx, 1);
    }
}
