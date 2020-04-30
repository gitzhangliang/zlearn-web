package com.zl.filter;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zl
 */
@WebFilter(urlPatterns = "/*",filterName = "loginFilter")
public class CorsFilter implements Filter {
	@Override

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("");
	}

	@Override

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse ) servletResponse;
		HttpServletRequest request = (HttpServletRequest ) servletRequest;
		String originHeader=request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", originHeader);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Disposition,Content-Length, Authorization, Accept,X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy(){ }


}
