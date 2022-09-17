package com.sundram.aictetaskmanagement.config;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sundram.aictetaskmanagement.service.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtil;

    Logger logger = Logger.getLogger("JWT filter logger");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // getting authorization header
        String requestTokenHeader = request.getHeader("Authorization");
        System.out.println(requestTokenHeader);
        String username = null;
        String jwtToken = null;
        /*
         * Checking the token,if it is not null and starts with Bearer if both of this
         * condition gets fullfilled then only we can proceed else we will not proceed
         * further
         */
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

            jwtToken = requestTokenHeader.substring(7);
            logger.info(jwtToken);
            try {
                username = this.jwtUtil.extractUsername(jwtToken);
                logger.info("I am username "+username);
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token is expired");
                logger.severe(e.toString());
            } catch (Exception e) {
                System.out.println("We run into the exception");
                logger.severe(e.toString());
            }
        } else {
            logger.severe("The request header is not fullfilling our criteria");
        }

        // validating the jwt token
        //todo
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            logger.info(userDetails.isAccountNonLocked()+" isAccountNonLocked");
            logger.info(userDetails.isEnabled()+" isEnabled");
            if (this.jwtUtil.validateToken(jwtToken, userDetails)) {
                // token is valid
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                        logger.info(""+ userDetails.getAuthorities().toString());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                // token is invalid
                System.out.println("Invalid token");
                logger.severe("JWTAuthenticationFilter says invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }

}
