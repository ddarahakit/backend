package com.ddarahakit.backend.user;

import com.ddarahakit.backend.user.model.GetEmailCertReq;
import com.ddarahakit.backend.user.model.LoginUser;
import com.ddarahakit.backend.user.model.PostSignupReq;
import com.ddarahakit.backend.user.model.PostSignupRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserProvider {
    @Autowired
    UserDao userDao;

    public LoginUser getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
}
