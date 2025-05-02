package com.Roo.demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class IFrameFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        var oldHeader = httpResponse.getHeader("Content-Security-Policy");
        if (oldHeader!=null) {
            oldHeader = oldHeader + " frame-src 'none';";
            httpResponse.setHeader("Content-Security-Policy", oldHeader);
        }
        else
            httpResponse.setHeader("Content-Security-Policy", "frame-src 'none';");
        httpResponse.setHeader("X-Frame-Options", " content=\"DENY\"");


        filterChain.doFilter(request, response);
    }
}