package com.github.ignacy123.projectvocabulary.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ignacy123.projectvocabulary.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

/**
 * Created by ignacy on 27.07.16.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService())
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JsonSecurityHandler jsonSecurityHandler() {
		return new JsonSecurityHandler(objectMapper);
	}

	@Bean
	public Filter jsonAuthenticationFilter() throws Exception {
		JsonAuthenticationFilter filter = new JsonAuthenticationFilter(objectMapper);
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(jsonSecurityHandler());
		filter.setAuthenticationFailureHandler(jsonSecurityHandler());
		filter.afterPropertiesSet();
		return filter;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserRepositoryUserDetailsService(userRepository);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable();
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/register")
				.permitAll()
				.antMatchers(HttpMethod.POST, "/registerWithUid")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.addFilterBefore(new SimpleCORSFilter(), JsonAuthenticationFilter.class)
				.addFilterBefore(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
				.authenticationEntryPoint(jsonSecurityHandler())
				.accessDeniedHandler(jsonSecurityHandler())
				.and()
                .addFilterBefore(new SimpleCORSFilter(), JsonAuthenticationFilter.class)
				.logout()
                .deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
				.logoutSuccessHandler(jsonSecurityHandler());

	}
}
