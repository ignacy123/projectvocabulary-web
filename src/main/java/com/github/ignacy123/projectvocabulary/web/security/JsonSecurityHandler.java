package com.github.ignacy123.projectvocabulary.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that serves as all necessary spring security interfaces to make authentication and security restful and json based.
 */
public class JsonSecurityHandler implements AccessDeniedHandler, AuthenticationEntryPoint, AuthenticationSuccessHandler, AuthenticationFailureHandler,
        LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    public JsonSecurityHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(403);
        setHeaders(request, response);
        response.getWriter().flush();
    }

    private void setHeaders(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setStatus(401);
        setHeaders(request, response);
        response.getWriter().flush();
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        User userDetails = (User) authentication.getPrincipal();

        response.setStatus(200);
        setHeaders(request, response);

        response.getWriter().write(objectMapper.writeValueAsString(userDetails));
        response.getWriter().flush();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(400);

        Map<String, String> resultFailure = new HashMap<>();

        setHeaders(request, response);
        resultFailure.put("login", "security.badCredentials");

        String responseString = objectMapper.writeValueAsString(resultFailure);
        response.getWriter().write(responseString);
        response.getWriter().flush();
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.setStatus(200);
        setHeaders(request, response);
        response.getWriter().flush();
    }

}