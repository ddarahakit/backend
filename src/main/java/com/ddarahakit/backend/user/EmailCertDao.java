package com.ddarahakit.backend.user;

import com.ddarahakit.backend.question.model.PostQuestionRes;
import com.ddarahakit.backend.question.model.PutQuestionReq;
import com.ddarahakit.backend.user.model.GetEmailCertReq;
import com.ddarahakit.backend.user.model.GetEmailCertRes;
import com.ddarahakit.backend.user.model.PostSignupReq;
import com.ddarahakit.backend.user.model.PostSignupRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class EmailCertDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Integer createToken(GetEmailCertReq getEmailCertReq) {

        String createTokenQuery = "INSERT INTO emailcert (token,user_email,expired,expired_timestamp) VALUES (?,?,FALSE, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL +5 MINUTE));";
        Object[] createTokenParams = new Object[]{getEmailCertReq.getToken(), getEmailCertReq.getEmail()};

        return this.jdbcTemplate.update(createTokenQuery, createTokenParams);
    }

    public Boolean tokenCheck(GetEmailCertReq getEmailCertReq) {
        String selectEmailCertQuery = "SELECT EXISTS(SELECT * FROM emailcert WHERE token=? AND user_email=? AND expired=FALSE AND expired_timestamp>CURRENT_TIMESTAMP)";
        Object[] selectEmailCertParams = new Object[]{
                getEmailCertReq.getToken(),
                getEmailCertReq.getEmail()
        };
        Integer exist = this.jdbcTemplate.queryForObject(selectEmailCertQuery, Integer.class, selectEmailCertParams);

        return exist == 1;
    }

    public GetEmailCertRes signupConfirm(String userEmail) {
        System.out.println(userEmail);
        String signupConfirmQuery = "UPDATE user SET enabled=TRUE WHERE email=?";
        Object[] signupConfirmParams = new Object[]{
                userEmail};

        return new GetEmailCertRes(this.jdbcTemplate.update(signupConfirmQuery, signupConfirmParams));

    }

}
