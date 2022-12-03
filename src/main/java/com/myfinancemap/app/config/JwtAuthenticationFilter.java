package com.myfinancemap.app.config;

import com.myfinancemap.app.exception.Error;
import com.myfinancemap.app.exception.ServiceExpection;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.repository.UserRepository;
import com.myfinancemap.app.security.JwtAuthentication;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    private static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECRET_KEY = "MyF1nänc3MäpPässw0rĐ!";
    public static final String AUDIENCE = "MyFinanceMap service";
    public static final String ISSUER = "MyFinanceMap";
    public static final int MAX_TIME_TO_LIVE_SEC = 3600;
    private static final HmacKey TOKEN_SECRET_KEY = new HmacKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    @Override
    @SneakyThrows({
            InvalidJwtException.class,
            MalformedClaimException.class,
            ChangeSetPersister.NotFoundException.class,
            ServletException.class,
            IOException.class})
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        final String authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeaderValue == null
                || !StringUtils.startsWithIgnoreCase(authorizationHeaderValue, TOKEN_PREFIX)) {
            throw new ServiceExpection(Error.MISSING_TOKEN);
        }

        final JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setVerificationKey(TOKEN_SECRET_KEY)
                .setExpectedAudience(AUDIENCE)
                .setExpectedIssuer(ISSUER)
                .setRelaxVerificationKeyValidation().build();

        final String substring = authorizationHeaderValue.substring(TOKEN_PREFIX.length());
        final JwtClaims jwtClaims = jwtConsumer.processToClaims(substring);

        final String subject = jwtClaims.getSubject();
        Optional<User> maybeUser = userRepository.getUserByUsername(subject);
        if (maybeUser.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }

        SecurityContextHolder.getContext().setAuthentication(
                new JwtAuthentication(subject, Collections.singletonList(map(maybeUser.get().getRole().name()))));

        filterChain.doFilter(request, response);
    }

    private GrantedAuthority map(final String role) {
        return new SimpleGrantedAuthority("ROLE_" + role);
    }
}