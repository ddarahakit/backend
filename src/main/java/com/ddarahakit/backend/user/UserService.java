package com.ddarahakit.backend.user;

import com.ddarahakit.backend.user.model.GetEmailCertReq;
import com.ddarahakit.backend.user.model.PostSignupReq;
import com.ddarahakit.backend.user.model.PostSignupRes;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PostSignupRes createUser(String token, PostSignupReq postSignupReq) {
        postSignupReq.setPassword(passwordEncoder.encode(postSignupReq.getPassword()));
        PostSignupRes postSignupRes = userDao.createUser(postSignupReq);

        emailCertDao.createToken(new GetEmailCertReq(token, postSignupReq.getEmail()));
        return postSignupRes;
    }
}
