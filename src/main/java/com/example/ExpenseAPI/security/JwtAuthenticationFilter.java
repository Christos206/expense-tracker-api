package com.example.ExpenseAPI.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Creates an object of this class and manages it.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtservice;
    private final UserDetailsService userdetailsservice;

    public JwtAuthenticationFilter (JwtService jwtservice, UserDetailsService userdetailsservice){
        this.jwtservice = jwtservice;
        this.userdetailsservice = userdetailsservice;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username;

        try {
            username = jwtservice.extractUsername(jwt);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }


        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userdetailsservice.loadUserByUsername(username);


            if(jwtservice.isTokenValid(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
