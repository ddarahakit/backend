package com.ddarahakit.backend.user;

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
        String createUserQuery = "insert into user (email, password, nickname) VALUES (?, ?, ?)";

        Object[] createUserParams = new Object[] { email, "kakao", nickname }; // 나중에 항목 추가를 위해

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";

        String createAuthorityQuery = "insert into authority values(?, ?)";

        Object[] createAuthorityParams = new Object[]{email, 0};

        this.jdbcTemplate.update(createAuthorityQuery, createAuthorityParams);

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Integer.class);
    }

    public LoginUser getUserByEmail(String email) {
        String getEmailQuery = "SELECT * FROM user LEFT OUTER JOIN authority on user.email=authority.user_email WHERE email=? AND enabled=1";

        return this.jdbcTemplate.queryForObject(getEmailQuery
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
}
