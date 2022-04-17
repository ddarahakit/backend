package com.ddarahakit.backend.user;


import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.user.model.PostLoginReq;
import com.ddarahakit.backend.user.model.PostLoginRes;
import com.ddarahakit.backend.user.model.PostSignupReq;
import com.ddarahakit.backend.user.model.PostSignupRes;
import com.ddarahakit.backend.utils.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private UserService userService;



    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<PostSignupRes> signup(@RequestBody PostSignupReq postSignupReq) throws Exception {
        PostSignupRes postSignupRes = userService.createUser(postSignupReq);

        return new BaseResponse<>(postSignupRes);
    }

    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq) throws Exception {

        Authentication authentication = authenticate(postLoginReq.getEmail(), postLoginReq.getPassword());
        User user = (User)authentication.getPrincipal();

        final String token = jwtTokenUtil.generateToken(user);

        return new BaseResponse<>(new PostLoginRes(token));
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
}
