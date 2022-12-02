package com.myfinancemap.app.util;

import com.myfinancemap.app.config.JwtAuthenticationFilter;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.domain.auth.AuthenticationToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jose4j.keys.HmacKey;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class TokenUtils {

    public static final String STATUS_VALID = "valid";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_INVALID = "invalid";

    private TokenUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    public static long timeDifference(final AuthenticationToken token) {
        final Calendar calendar = Calendar.getInstance();
        return token.getExpiryDate().getTime() - calendar.getTime().getTime();
    }

    public static String createJwtToken(final User user) {
        return Jwts.builder()
                .claim("name", user.getUsername())
                .setSubject(user.getUsername())
                .setId(user.getUserId().toString())
                .setIssuedAt(new Date())
                .setAudience(JwtAuthenticationFilter.AUDIENCE)
                .setIssuer(JwtAuthenticationFilter.ISSUER)
                .setExpiration(Date.from(LocalDateTime.now().plusSeconds(JwtAuthenticationFilter.MAX_TIME_TO_LIVE_SEC)
                        .toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256,
                        new HmacKey(JwtAuthenticationFilter.SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}