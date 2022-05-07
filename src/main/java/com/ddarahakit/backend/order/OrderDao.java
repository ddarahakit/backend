package com.ddarahakit.backend.order;


import com.ddarahakit.backend.course.model.PostCourseReq;
import com.ddarahakit.backend.course.model.PostCourseRes;
import com.ddarahakit.backend.payment.model.PostOrderRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OrderDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Integer orderPriceCheck(Integer courseIdx){
        String orderPriceCheckQuery = "SELECT price FROM course WHERE idx=?";

        Integer price = this.jdbcTemplate.queryForObject(orderPriceCheckQuery, Integer.class, courseIdx);

        return price;
    }

    public PostOrderRes createOrder(Integer courseIdx, String userEmail, String impUid) {
        String createOrderQuery = "insert into `order` (course_idx, user_email, imp_uid) VALUES (?, ?, ?)";
        Object[] createOrderParams = new Object[] {
                courseIdx,
                userEmail,
                impUid
        };

        this.jdbcTemplate.update(createOrderQuery, createOrderParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);

        return new PostOrderRes(lastInsertIdx, 1);
    }
}
