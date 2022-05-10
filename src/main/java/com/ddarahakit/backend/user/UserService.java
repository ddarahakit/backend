package com.ddarahakit.backend.user;

import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.user.model.*;
import com.ddarahakit.backend.utils.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;



@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDao userDao;

    @Autowired
    EmailCertDao emailCertDao;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    public PostSignupRes createUser(String token, PostSignupReq postSignupReq) {
        postSignupReq.setPassword(passwordEncoder.encode(postSignupReq.getPassword()));
        PostSignupRes postSignupRes = userDao.createUser(postSignupReq);

        emailCertDao.createToken(new GetEmailCertReq(token, postSignupReq.getEmail()));
        return postSignupRes;
    }

    public void createRefreshToken(String email, String refreshToken) {
        userDao.createRefreshToken(email, refreshToken);
    }

    public BaseResponse<PostLoginRes> refreshToken(String userEmail, String accessToken, String refreshToken) {
        if(!jwtTokenUtil.isTokenExpired(accessToken)) {
            throw new AccessDeniedException("");
        }
        User user = userDao.getUserByEmail(userEmail);

        if(jwtTokenUtil.isTokenExpired(refreshToken) || !userDao.getRefreshToken(user.getUsername()).equals(refreshToken)) {
            throw new AccessDeniedException("");
        }

        final String newAccessToken = jwtTokenUtil.generateToken(user);
        final String newRefreshToken = jwtTokenUtil.generateRefreshToken();

        userDao.updateRefreshToken(user.getUsername(), newRefreshToken);

        return new BaseResponse<>(new PostLoginRes(newAccessToken, newRefreshToken));
    }


}
