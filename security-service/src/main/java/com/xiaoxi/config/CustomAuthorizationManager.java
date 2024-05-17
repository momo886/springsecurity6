package com.xiaoxi.config;


import com.xiaoxi.entity.Permission;
import com.xiaoxi.service.PermissionService;
import com.xiaoxi.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Author： momo
 *
 * 自定义动态权限判断
 */
@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext>  {
    AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    @Autowired
    private PermissionService permissionService;


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        boolean granted = authentication.get() != null && !this.trustResolver.isAnonymous(authentication.get())
                && authentication.get().isAuthenticated();
        if(!granted){
            return new AuthorizationDecision(granted);
        }
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        String requestURI = request.getRequestURI();

        Long userId = TokenUtils.getUserId();
        List<Permission> permissions = permissionService.listByUserId(userId);
        List<String> paths = permissions.stream().map(Permission::getPath).collect(Collectors.toList());
        if(!paths.contains(requestURI)){
            return new AuthorizationDecision(false);
        }
        return new AuthorizationDecision(true);
    }
}