package com.ddarahakit.backend.question;

import com.ddarahakit.backend.course.model.GetCourseWithImageRes;
import com.ddarahakit.backend.question.model.GetQuestionRes;
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
import java.util.List;

@Repository
public class QuestionDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostQuestionRes createQuestion(String userEmail, @RequestBody PostQuestionReq postQuestionReq) {
        String createQuestionQuery = "insert into question (title, contents, chapter_idx, user_email) " +
                "VALUES (?, ?, ?, ?)";
        Object[] createQuestionParams = new Object[] {
                postQuestionReq.getTitle(),
                postQuestionReq.getContents(),
                postQuestionReq.getChapter_idx(),
                userEmail};

        this.jdbcTemplate.update(createQuestionQuery, createQuestionParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);

        return new PostQuestionRes(lastInsertIdx, 1);
    }

    public PostQuestionRes deleteQuestion(String userEmail, Integer questionIdx) {
        String deleteQuestionQuery = "UPDATE question SET status=0 WHERE idx=? AND user_email=?";
        Object[] deleteQuestionParams = new Object[] {
                questionIdx,
                userEmail};

        return new PostQuestionRes(questionIdx, this.jdbcTemplate.update(deleteQuestionQuery, deleteQuestionParams));
    }

    public List<GetQuestionRes> getQuestionList() {

        String getCourseQuery = "SELECT * FROM question";

        return this.jdbcTemplate.query(getCourseQuery
                , (rs,rowNum) -> new GetQuestionRes(
                        rs.getInt("idx"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("user_email"),
                        rs.getTimestamp("create_timestamp"),
                        rs.getTimestamp("update_timestamp")));
    }

    public List<GetQuestionRes> getQuestionListByChapter(Integer chapterIdx) {

        String getCourseQuery = "SELECT * FROM question WHERE chapter_idx=?";

        return this.jdbcTemplate.query(getCourseQuery
                , (rs,rowNum) -> new GetQuestionRes(
                        rs.getInt("idx"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("user_email"),
                        rs.getTimestamp("create_timestamp"),
                        rs.getTimestamp("update_timestamp")), chapterIdx);
    }

    public List<GetQuestionRes> getQuestionListByUserEmail(String userEmail) {

        String getCourseQuery = "SELECT * FROM question WHERE user_email=?";

        return this.jdbcTemplate.query(getCourseQuery
                , (rs,rowNum) -> new GetQuestionRes(
                        rs.getInt("idx"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("user_email"),
                        rs.getTimestamp("create_timestamp"),
                        rs.getTimestamp("update_timestamp")), userEmail);
    }
}
