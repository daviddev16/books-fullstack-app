package org.books.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.books.security.model.UserDetailsData;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public final class TokenUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_PREFIX = "Bearer ";

    public static String create(Authentication authentication) {
        return JWT.create()
                .withIssuedAt(new Date())
                .withSubject(((UserDetailsData) authentication.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Secrets.getTokenExpiresAt()))
                .sign(Algorithm.HMAC512(Secrets.getTokenSecret()));
    }

    public static String fromRequest(HttpServletRequest request) {
        final String headerAuth = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(AUTHORIZATION_PREFIX)) {
            return headerAuth.replace(AUTHORIZATION_PREFIX, "");
        }
        return null;
    }

    public static DecodedJWT decoded(String token) {
        return JWT.require(Algorithm.HMAC512(Secrets.getTokenSecret()))
                .build()
                .verify(token);
    }

    public static Claim getClaim(String token, String claimName) {
        return decoded(token).getClaim(claimName);
    }

    public static boolean valid(String token) {
        return getUsername(token) != null && !isExpired(token);
    }

    public static String getUsername(String token) {
        return decoded(token).getSubject();
    }

    public static Date getExpiresAt(String token) {
        return decoded(token).getExpiresAt();
    }

    public static boolean isExpired(String token) {
        return getExpiresAt(token).before(new Date());
    }



}
