package com.csun.mall.config;

import com.csun.mall.config.filter.AuthenticationFilter;
import com.csun.mall.config.handler.AuthenticationHandler;
import com.csun.mall.config.provider.UserAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author cxr
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationFilter authenticationFilter;

    private final AuthenticationHandler authenticationHandler;

    private final AuthenticationProvider authenticationProvider;

    private final UserAuthenticationDetailsSource userAuthenticationDetailsSource;

    @Autowired
    public WebSecurityConfig(AuthenticationFilter authenticationFilter,
                             AuthenticationHandler authenticationHandler,
                             AuthenticationProvider authenticationProvider,
                             UserAuthenticationDetailsSource userAuthenticationDetailsSource) {
        this.authenticationFilter = authenticationFilter;
        this.authenticationHandler = authenticationHandler;
        this.authenticationProvider = authenticationProvider;
        this.userAuthenticationDetailsSource = userAuthenticationDetailsSource;
    }


    /**
     *
     */
    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS);
    }

    /**
     *
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationProvider);
    }

    /**
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html",
                        "/swagger-ui/*",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/webjars/**","/static/**","/swagger-ui/*","/webjars/**","/v3/**","/doc.html","/swagger-ui.html"
                        ,"/swagger-resources/**","/v2/api-docs","/admin/**").permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/user/login")
                .successHandler(this.authenticationHandler)
                .failureHandler(this.authenticationHandler)
                .authenticationDetailsSource(userAuthenticationDetailsSource)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessHandler(this.authenticationHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationHandler)
                .and()
                .csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().cacheControl();
    }

    /**
     *
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    @Bean
    public FilterRegistrationBean processCorsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
