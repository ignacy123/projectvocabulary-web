package com.github.ignacy123.projectvocabulary.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(JsonAuthenticationFilter.class);

    private final ObjectMapper objectMapper;

    public JsonAuthenticationFilter(ObjectMapper objectMapper) {
        super();
        this.objectMapper = objectMapper;
    }


    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username;
        String password;

        try {
            LoginAttemptDto loginAttemptDto = objectMapper.readValue(request.getReader(), LoginAttemptDto.class);
            username = loginAttemptDto.getEmail();
            password = loginAttemptDto.getPassword();
        } catch (Exception e) {
            LOGGER.debug("Authentication JSON parsing failed.", e);
            throw new BadCredentialsException("Bad JSON format.", e);
        }

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


}
