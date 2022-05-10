package com.ddarahakit.backend.utils.oauth2;

import com.ddarahakit.backend.user.UserProvider;
import com.ddarahakit.backend.user.UserService;
import com.ddarahakit.backend.user.model.LoginUser;
import com.ddarahakit.backend.utils.jwt.JwtTokenUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserProvider userProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        Map<String, Object> kakao_account = (Map<String, Object>)oAuth2User.getAttributes().get("kakao_account");
        String email = (String)kakao_account.get("email");
        LoginUser loginUser = userProvider.getUserByEmail(email);

        String jwt = jwtTokenUtil.generateToken(loginUser);

        String url = makeRedirectUrl(jwt);
        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }


    private String makeRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/jwt/"+token)
                .build().toUriString();
    }
}