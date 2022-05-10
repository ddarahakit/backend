package com.ddarahakit.backend.user;

import com.ddarahakit.backend.question.model.PostQuestionRes;
import com.ddarahakit.backend.question.model.PutQuestionReq;
import com.ddarahakit.backend.user.model.Authority;
import com.ddarahakit.backend.user.model.LoginUser;
import com.ddarahakit.backend.user.model.PostSignupReq;
import com.ddarahakit.backend.user.model.PostSignupRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Arrays;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public PostSignupRes createUser(PostSignupReq postSignupReq) {

        String createUserQuery = "insert into user (email, password, nickname ) VALUES (?, ?, ?)";

        Object[] createUserParams = new Object[]{postSignupReq.getEmail(), postSignupReq.getPassword(), postSignupReq.getNickname()};

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        String createAuthorityQuery = "insert into authority values(?, ?)";

        Object[] createAuthorityParams = new Object[]{postSignupReq.getEmail(), 0};

        this.jdbcTemplate.update(createAuthorityQuery, createAuthorityParams);

        return new PostSignupRes(lastInsertIdx, 1);
    }

    public Integer createUserByKakao(String email, String nickname) {
        String createUserQuery = "insert into user (email, password, nickname, enabled) VALUES (?, ?, ?, 1)";

        Object[] createUserParams = new Object[] { email, "kakao", nickname }; // 나중에 항목 추가를 위해

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";

        String createAuthorityQuery = "insert into authority values(?, ?)";

        Object[] createAuthorityParams = new Object[]{email, 0};

        this.jdbcTemplate.update(createAuthorityQuery, createAuthorityParams);

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Integer.class);
    }

    public LoginUser getUserByEmail(String email) {
        String getUserByEmailQuery = "SELECT * FROM `user` LEFT OUTER JOIN authority on `user`.email=authority.user_email WHERE email=? AND enabled=1";

        return this.jdbcTemplate.queryForObject(getUserByEmailQuery
                , (rs, rowNum) -> new LoginUser(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        Arrays.asList(new SimpleGrantedAuthority(Authority.values()[rs.getObject("role", int.class)].toString()))
                ), email);
    }

    public Boolean checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from user where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                Boolean.class,
                checkEmailParams);
    }

    public void createRefreshToken(String email, String refreshToken) {

        String createRefreshTokenQuery = "insert into `refreshtoken` (user_email, token ) VALUES (?, ?)";

        Object[] createRefreshTokenParams = new Object[]{email, refreshToken};

        this.jdbcTemplate.update(createRefreshTokenQuery, createRefreshTokenParams);
    }

    public String getRefreshToken(String email) {

        String getRefreshTokenQuery = "SELECT token FROM `refreshtoken` WHERE user_email=? ORDER BY create_timestamp LIMIT 1";


        return this.jdbcTemplate.queryForObject(getRefreshTokenQuery
                , (rs, rowNum) -> rs.getString("token"), email);
    }

    public void updateRefreshToken(String email, String newRefreshToken) {

        String updateRefreshTokenQuery = "UPDATE refreshtoken SET token=? WHERE user_email=?";

        Object[] updateRefreshTokenParams = new Object[]{newRefreshToken, email};

        this.jdbcTemplate.update(updateRefreshTokenQuery, updateRefreshTokenParams);
    }

    public PostQuestionRes updateQuestion(String userEmail, Integer questionIdx, PutQuestionReq putQuestionReq) {
        String createQuestionQuery = "UPDATE question SET title=?, contents=? WHERE user_email=? AND idx=?";
        Object[] createQuestionParams = new Object[] {
                putQuestionReq.getTitle(),
                putQuestionReq.getContents(),
                userEmail,
                questionIdx};

        this.jdbcTemplate.update(createQuestionQuery, createQuestionParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);

        return new PostQuestionRes(lastInsertIdx, 1);
    }
}
