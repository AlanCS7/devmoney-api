package com.alancss.devmoneyapi.token;

import com.alancss.devmoneyapi.config.properties.DevmoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

    @Autowired
    private DevmoneyApiProperty devmoneyApiProperty;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getMethod().getName().equals("postAccessToken");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken oAuth2AccessToken, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;

        String refreshToken = oAuth2AccessToken.getRefreshToken().getValue();
        adicionarRefreshTokenNoCookie(refreshToken, request, response);
        removerRefreshTokenDoBody(token);

        return oAuth2AccessToken;
    }

    private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }

    private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(devmoneyApiProperty.getSeguranca().isEnableHttps());
        refreshTokenCookie.setPath(request.getContextPath() + "oauth/token");
        refreshTokenCookie.setMaxAge(2592000);
        response.addCookie(refreshTokenCookie);
    }

}
