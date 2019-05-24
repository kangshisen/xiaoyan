package com.kss.shiro_demo.config;

import com.kss.shiro_demo.entity.UserInfo;
import com.kss.shiro_demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: shiro_demo
 * @Date: 2019-05-21 10:15
 * @Author: 康仕森
 * @Description:
 */
public class MyShiroRelam extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        simpleAuthorizationInfo.addStringPermission("");
        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     * token.getUsername()  //获得用户名 String
     * token.getPrincipal() //获得用户名 Object
     * token.getPassword()  //获得密码 char[]
     * token.getCredentials() //获得密码 Object
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        UserInfo byUsername = userService.findByUsername(usernamePasswordToken.getUsername());
        if(byUsername==null){
            return null;//用户名错误
        }
        return new SimpleAuthenticationInfo(byUsername,byUsername.getPassword(),getName());
    }
}
