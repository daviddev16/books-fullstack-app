package org.books.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.books.payload.request.UserSignInPayload;
import org.books.security.model.UserDetailsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * UPA stands for UsernamePasswordAuthentication
 */
public final class Utility {



    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(Utility.class);

    public static UsernamePasswordAuthenticationToken getUPAToken(String username, String password) {
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    public static UsernamePasswordAuthenticationToken getUPAToken(UserDetailsData userDetailsData,
                                                                  HttpServletRequest request) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetailsData, null, userDetailsData.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }

    public static Authentication authenticate(AuthenticationManager authenticationManager,
                                              UserSignInPayload signInPayload) {

        UsernamePasswordAuthenticationToken auth = getUPAToken(signInPayload.getUsername(),
                signInPayload.getPassword());

        return authenticationManager.authenticate(auth);
    }

}
