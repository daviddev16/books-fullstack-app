package org.books.security.filter;

import org.books.security.model.UserDetailsData;
import org.books.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.books.security.utils.TokenUtil.*;
import static org.books.security.utils.Utility.getUPAToken;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger LOG = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String userToken = fromRequest(request);
        if (userToken != null && valid(userToken)) {
            try {
                String username = getUsername(userToken);
                UserDetailsData userDetails = (UserDetailsData) userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = getUPAToken(userDetails, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                LOG.error("Cannot set a user authentication: " + e.getMessage(), e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
