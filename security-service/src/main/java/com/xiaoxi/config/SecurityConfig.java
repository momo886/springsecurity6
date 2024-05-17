package com.xiaoxi.config;

import com.xiaoxi.except.CustomAccessDeniedHandler;
import com.xiaoxi.except.CustomAuthenticationEntryPoint;
import com.xiaoxi.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

/**
 * @Author： momo
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsServiceImpl;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthorizationManager<RequestAuthorizationContext> auth) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll() //登入放行
//                                .anyRequest().authenticated()
                        .anyRequest().access(auth)
                );


        //禁用登录页面
        http.formLogin(AbstractHttpConfigurer::disable);
        //禁用登出页面
        http.logout(AbstractHttpConfigurer::disable);
        //禁用session
        http.sessionManagement(AbstractHttpConfigurer::disable);
        //禁用httpBasic
        http.httpBasic(AbstractHttpConfigurer::disable);
        //禁用csrf保护
        http.csrf(AbstractHttpConfigurer::disable);
//        //在认证前增加token过滤器
        http.addFilterBefore(new AuthFilter(), AuthorizationFilter.class);
        //自定义异常拦截
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler());
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = daoAuthenticationProvider();
        MobileAuthenticationProvider mobileAuthenticationProvider = mobileAuthenticationProvider();
        return new ProviderManager(authenticationProvider,mobileAuthenticationProvider);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }


    @Bean
    public MobileAuthenticationProvider mobileAuthenticationProvider() {
        MobileAuthenticationProvider mobilAuthenticationProvider = new MobileAuthenticationProvider();
        mobilAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        return mobilAuthenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}