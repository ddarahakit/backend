package com.ddarahakit.backend.question;

import com.ddarahakit.backend.question.model.PostQuestionRes;
import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.question.model.PostQuestionReq;
import com.ddarahakit.backend.question.model.PostQuestionRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sql.DataSource;

@Repository
public class QuestionDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostQuestionRes createQuestion(@RequestBody PostQuestionReq postQuestionReq) {
        String createQuestionQuery = "insert into question (title, contents, chapter_idx, user_email) " +
                "VALUES (?, ?, ?, ?)";
        Object[] createQuestionParams = new Object[] {
                postQuestionReq.getTitle(),
                postQuestionReq.getContents(),
                postQuestionReq.getChapter_idx(),
                postQuestionReq.getUser_email()};

        this.jdbcTemplate.update(createQuestionQuery, createQuestionParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);

        return new PostQuestionRes(lastInsertIdx, 1);
    }

}
