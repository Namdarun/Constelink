package com.srp.constelinkmember.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.srp.constelinkmember.security.handler.OAuth2AuthenticationFailureHandler;
import com.srp.constelinkmember.security.handler.OAuth2AuthenticationSuccessHandler;
import com.srp.constelinkmember.security.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.srp.constelinkmember.security.service.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;

	private final PrincipalOauth2UserService principalOauth2UserService;

	private final OAuth2AuthenticationSuccessHandler successHandler;
	private final OAuth2AuthenticationFailureHandler failureHandler;

	private final CorsFilter corsFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			// 기본 REST API 사용
			.httpBasic().disable()

			// JWT Token 방식이라 csrf disable
			.csrf().disable()
			// cross origin 설정
			
			.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
			// Oauth login 만을 사용할 것이기 때문에 formLogin disable
			.formLogin().disable()
			.authorizeHttpRequests()
			.anyRequest().permitAll()
			.and()
			.oauth2Login()
			.authorizationEndpoint()
			.baseUri("/oauth2/authorization")
			.authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository)
			.and()
			.redirectionEndpoint()
			.baseUri("/oauth2/callback/*")
			.and()
			.userInfoEndpoint()
			.userService(principalOauth2UserService)
			.and()
			.successHandler(successHandler)
			.failureHandler(failureHandler);

		return httpSecurity.build();
	}

}
