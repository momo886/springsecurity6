package com.xiaoxi.config;

import com.xiaoxi.except.BusinessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author： momo
 *
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        String phone = mobileAuthenticationToken.getPhone();
        String mobileCode = mobileAuthenticationToken.getMobileCode();
        //todo 改成从redis获取验证码判断
        // 判断验证码是否一致
        if (!mobileCode.equals("123456")) {
            throw new BusinessException("验证码错误");
        }
        // 如果验证码一致，从数据库中读取该手机号对应的用户信息
        UserDetails loadedUser = (UserDetails) userDetailsService.loadUserByUsername(phone);
        if (loadedUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new MobileAuthenticationToken(loadedUser, null, loadedUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return MobileAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
