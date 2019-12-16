package com.umati.springboot.config;

import org.apache.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CoresFilterSpringBoot implements Filter {
    private  static final Logger logger=Logger.getLogger(CoresFilterSpringBoot.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "access_token, authorization, content-type");

        logger.info("Custom Filter is called: "+((HttpServletRequest)servletRequest).getRequestURL());
        logger.info("Filter: "+((HttpServletResponse)servletResponse).getHeaders("Access-Control-Allow-Origin"));
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpStatus.OK.value());
        } else { filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {
    }
}
