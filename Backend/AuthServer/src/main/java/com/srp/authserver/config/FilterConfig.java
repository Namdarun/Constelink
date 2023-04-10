package com.srp.authserver.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.srp.authserver.filters.AdminFilter;
import com.srp.authserver.filters.GlobalFilter;
import com.srp.authserver.filters.HospitalFilter;
import com.srp.authserver.jwt.TokenProvider;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

	private final TokenProvider tokenProvider;
	private final RedisTemplate<String, String> redisTemplate;

	@Bean
	public FilterRegistrationBean<GlobalFilter> globalFilter() {
		FilterRegistrationBean<GlobalFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new GlobalFilter(tokenProvider, redisTemplate));
		registrationBean.addUrlPatterns("/auth/role/member");
		registrationBean.setOrder(0);
		registrationBean.setName("GloabalFilter");
		registrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<AdminFilter> adminFilter() {
		FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AdminFilter(tokenProvider, redisTemplate));
		registrationBean.addUrlPatterns("/auth/role/admin");
		registrationBean.setOrder(1);
		registrationBean.setName("AdminFilter");
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<HospitalFilter> hospitalFilter() {
		FilterRegistrationBean<HospitalFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new HospitalFilter(tokenProvider, redisTemplate));
		registrationBean.addUrlPatterns("/auth/role/hospital");
		registrationBean.setOrder(1);
		registrationBean.setName("HospitalFilter");
		return registrationBean;
	}

}
