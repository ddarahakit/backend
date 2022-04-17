package com.ddarahakit.backend.user;

import com.ddarahakit.backend.user.model.Authority;
import com.ddarahakit.backend.user.model.PostSignupReq;
import com.ddarahakit.backend.user.model.PostSignupRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Arrays;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public PostSignupRes createUser(PostSignupReq postSignupReq) {

        String createUserQuery = "insert into user (email, password ) VALUES (?, ?)";

        Object[] createUserParams = new Object[]{postSignupReq.getEmail(), postSignupReq.getPassword()};

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        String createAuthorityQuery = "insert into authority values(?, ?)";

        Object[] createAuthorityParams = new Object[]{postSignupReq.getEmail(), 0};

        this.jdbcTemplate.update(createAuthorityQuery, createAuthorityParams);

        return new PostSignupRes(lastInsertIdx, 1);
    }


    public User getUserByEmail(String email) {
        String getEmailQuery = "SELECT * FROM user LEFT OUTER JOIN authority on user.email=authority.user_email WHERE email=?";

        return this.jdbcTemplate.queryForObject(getEmailQuery
                , (rs, rowNum) -> new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        Arrays.asList(new SimpleGrantedAuthority(Authority.values()[rs.getObject("role", int.class)].toString()))
                ), email);
    }
}
