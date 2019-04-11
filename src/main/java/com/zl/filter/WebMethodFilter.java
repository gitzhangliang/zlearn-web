package com.zl.filter;

import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tzxx
 */
@WebFilter(filterName = "webMethodFilter", urlPatterns = "/*")
public class WebMethodFilter implements Filter {


    @Override
    public void init(FilterConfig config) throws ServletException {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse ) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest ) request;
        String method = httpServletRequest.getMethod();
        if (method.equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(200);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}