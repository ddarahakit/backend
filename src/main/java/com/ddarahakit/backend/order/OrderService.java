package com.ddarahakit.backend.order;

import com.ddarahakit.backend.payment.model.PostOrderRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    public Integer orderPriceCheck(Integer courseIdx){
        return orderDao.orderPriceCheck(courseIdx);
    }

    public PostOrderRes createOrder(Integer courseIdx, String userEmail, String impUid){
        return orderDao.createOrder(courseIdx, userEmail, impUid);
    }
}
