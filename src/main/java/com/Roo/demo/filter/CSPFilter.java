package com.Roo.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class CSPFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        SecureRandom random = new SecureRandom();

        byte[] nonceBytes = new byte[16];
        byte[] nonceStyleBytes = new byte[16];

        random.nextBytes(nonceBytes);
        random.nextBytes(nonceStyleBytes);

        String nonce = Base64.getEncoder().encodeToString(nonceBytes);
        String nonceStyle = Base64.getEncoder().encodeToString(nonceStyleBytes);

        String policy = "default-src 'self'; script-src 'self' 'nonce-" + nonce + "'  img-src 'self'; object-src 'none'; style-src 'self' 'nonce-"+nonceStyle+"'";

        var oldHeader = httpResponse.getHeader("Content-Security-Policy");
        if (oldHeader!=null) {
            oldHeader = oldHeader + " " + policy;
            httpResponse.setHeader("Content-Security-Policy", oldHeader);
        }
        else
            httpResponse.setHeader("Content-Security-Policy", policy);
//        httpResponse.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'   'nonce-" + nonce + "' localhost:8080");

        request.setAttribute("nonce", nonce);
        request.setAttribute("nonceStyle", nonceStyle);

        filterChain.doFilter(request, response);
    }
}