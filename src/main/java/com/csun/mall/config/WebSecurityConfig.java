package com.csun.mall.config;

import com.csun.mall.config.filter.AuthenticationFilter;
//import com.csun.mall.config.filter.CustomerAuthenticationFilter;
import com.csun.mall.config.filter.CustomerAuthenticationFilter;
import com.csun.mall.config.handler.AuthenticationHandler;
import com.csun.mall.config.provider.UserAuthenticationDetailsSource;
import com.csun.mall.domain.CsrMember;
import com.csun.mall.service.CsrMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * @author cxr
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Configuration
    @Order(2)
    static class AdminConfigurerAdapter extends WebSecurityConfigurerAdapter{

        @Autowired
        private AuthenticationFilter authenticationFilter;
        @Autowired
        private AuthenticationHandler authenticationHandler;
        @Autowired
        private AuthenticationProvider authenticationProvider;
        @Autowired
        private UserAuthenticationDetailsSource userAuthenticationDetailsSource;
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
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(this.authenticationProvider);
//        auth.userDetailsService(userDetailsService);
        }

        /**
         *
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
            http.authorizeRequests()
                    .antMatchers("/swagger-ui.html",
                            "/swagger-ui/*",
                            "/swagger-resources/**",
                            "/v2/api-docs",
                            "/v3/api-docs",
                            "/webjars/**","/static/**","/swagger-ui/*","/webjars/**","/v3/**","/doc.html","/swagger-ui.html"
                            ,"/swagger-resources/**","/v2/api-docs","/user/login","/user/register","/customer/**","/index/**," +
                                    "/csunsolartech.oss-accelerate.aliyuncs.com/**").permitAll()
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
    }

    @Configuration
    @Order(1)
    static class CustomerConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private CsrMemberService csrMemberService;
        @Autowired
        private AuthenticationHandler authenticationHandler;
        @Autowired
        private CustomerAuthenticationFilter authenticationFilter;
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//            daoAuthenticationProvider.setUserDetailsService(new UserDetailsService() {
//                @Override
//                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                    CsrMember csrMember = csrMemberService.queryByUsername(username);
//                    return new User(csrMember.getUsername(), csrMember.getPassword(), true, true, true, true, null);
//                }
//            });
//            auth.authenticationProvider(daoAuthenticationProvider);
//        }
//
//        @Override
//        public void configure(WebSecurity web) {
//            try {
//                super.configure(web);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
            http.authorizeRequests()
//                    .antMatchers().permitAll()
                    .antMatchers("/customer/passport/b","/customer/passport/logout").authenticated()
                    .anyRequest().permitAll()
//                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(this.authenticationHandler)
                    .and()
                    .csrf().disable();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.headers().cacheControl();

        }
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


}
