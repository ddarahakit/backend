package com.ddarahakit.backend.user;

import com.ddarahakit.backend.user.model.PostSignupReq;
import com.ddarahakit.backend.user.model.PostSignupRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDao userDao;

    public PostSignupRes createUser(PostSignupReq postSignupReq) {
        postSignupReq.setPassword(passwordEncoder.encode(postSignupReq.getPassword()));
        return userDao.createUser(postSignupReq);
    }
}
