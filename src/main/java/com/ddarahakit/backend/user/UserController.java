package com.ddarahakit.backend.user;


import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.user.model.*;
import com.ddarahakit.backend.utils.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private UserService userService;

    @Autowired
    private EmailCertService emailCertService;


    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<PostSignupRes> signup(@RequestBody PostSignupReq postSignupReq) throws Exception {
        String token = UUID.randomUUID().toString();
        PostSignupRes postSignupRes = userService.createUser(token, postSignupReq);
        emailCertService.createEmailConfirmationToken(token,postSignupReq.getEmail());
        return new BaseResponse<>(postSignupRes);
    }

    @ResponseBody
    @GetMapping("/confirm")
    public BaseResponse<GetEmailCertRes> signupConfirm(GetEmailCertReq getEmailCertReq) throws Exception {
        GetEmailCertRes getEmailCertRes = emailCertService.signupConfirm(getEmailCertReq);
        return new BaseResponse<>(getEmailCertRes);
    }

    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq) throws Exception {

        Authentication authentication = authenticate(postLoginReq.getEmail(), postLoginReq.getPassword());
        User user = (User)authentication.getPrincipal();

        final String token = jwtTokenUtil.generateToken(user);
        final String refreshToken = jwtTokenUtil.generateRefreshToken();
        userService.createRefreshToken(user.getUsername(), refreshToken);
        return new BaseResponse<>(new PostLoginRes(token, refreshToken));
    }

    private Authentication authenticate(String email, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @ResponseBody
    @PostMapping("/refresh")
    public BaseResponse<PostLoginRes> refresh(@RequestHeader(value="UserEmail") String userEmail,
                                              @RequestHeader(value="AccessToken") String accessToken,
                                              @RequestHeader(value="RefreshToken") String refreshToken) {
        System.out.println("userEmail : " + userEmail);
        return userService.refreshToken(userEmail, accessToken, refreshToken);
    }
}
