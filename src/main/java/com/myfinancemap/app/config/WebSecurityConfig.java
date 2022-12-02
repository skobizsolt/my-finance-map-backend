package com.myfinancemap.app.config;

import com.myfinancemap.app.persistence.repository.UserRepository;
import com.myfinancemap.app.security.CustomMethodSecurityExpressionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Autowired
    private ApplicationContext applicationContext;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserRepository userRepository;

    private static final String[] FILTERED_URLS = {
            "/api/users/**",
            "/api/transactions/**",
            "/api/auth/change-password",
            "/api/shops/update",
            "/api/shops/delete/*"
    };

    private static final String[] UNFILTERED_URLS = {
            "/api/auth/**",
            "/api/shops",
            "/api/shops/*",
            "/api/shops/new",
            "/api/shops/map",
            "/swagger-ui/**",
            "/api-docs/**"
    };

    public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .cors()
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "**").permitAll()
                .antMatchers(UNFILTERED_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(UNFILTERED_URLS);
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> logFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(userRepository));
        registrationBean.addUrlPatterns(FILTERED_URLS);
        return registrationBean;
    }

    @Bean
    public CorsFilter corsFilter() {
        final CorsConfiguration corsConfig = new CorsConfiguration();
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        corsConfig.setAllowCredentials(false);
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.addExposedHeader("");
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsFilter(source);
    }

    // dummy implementation to suppress some default Spring Security configuration
    @Bean
    public UserDetailsService dummyUserDetailsService() {
        return username -> null;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        var expressionHandler = new CustomMethodSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
    /**
     * AccessDecisionManager makes a final access control (authorization) decision.
     * Overriding this method is necessary for role hierarchy, customized security methods..
     */
    @Override
    protected AccessDecisionManager accessDecisionManager() {

        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();

        var expresionAdvice= new ExpressionBasedPreInvocationAdvice();
        expresionAdvice.setExpressionHandler(getExpressionHandler());

        decisionVoters.add(new PreInvocationAuthorizationAdviceVoter(expresionAdvice));
        decisionVoters.add(new AuthenticatedVoter()); //It is necessary to add this one when we override the default AccessDecisionManager
        return new AffirmativeBased(decisionVoters);
    }
}
