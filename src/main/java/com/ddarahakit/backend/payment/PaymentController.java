package com.ddarahakit.backend.payment;

import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.order.OrderService;
import com.ddarahakit.backend.payment.model.PostOrderInfo;
import com.ddarahakit.backend.payment.model.PostOrderRes;
import com.ddarahakit.backend.user.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.ddarahakit.backend.config.BaseResponseStatus.PAYMENT_ERROR;
import static com.ddarahakit.backend.config.BaseResponseStatus.PAYMENT_PRICE_ERROR;

@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    OrderService orderService;

    @PostMapping("/order/payment/complete")
    public BaseResponse<PostOrderRes> paymentComplete(@RequestBody PostOrderInfo postOrderInfo, @AuthenticationPrincipal LoginUser loginUser) throws IOException {

        String token = paymentService.getToken();

        Integer amount = paymentService.paymentInfo(postOrderInfo.getImpUid(), token);
        try {

            Integer orderPriceCheck = orderService.orderPriceCheck(postOrderInfo.getCourseIdx());

            if (!orderPriceCheck.equals(amount)) {
                paymentService.payMentCancle(token, postOrderInfo.getImpUid(), amount, "결제 금액 오류");
                return new BaseResponse<>(PAYMENT_PRICE_ERROR);
            }

            return new BaseResponse<>(orderService.createOrder(postOrderInfo.getCourseIdx(), loginUser.getEmail(), postOrderInfo.getImpUid()));

        } catch (Exception e) {
            paymentService.payMentCancle(token, postOrderInfo.getImpUid(), amount, "결제 에러");
            return new BaseResponse<>(PAYMENT_ERROR);
        }
    }
}
