package com.kss.shiro_demo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: shiro_demo
 * @Date: 2019-05-21 14:01
 * @Author: 康仕森
 * @Description:
 */
/**
 * anon	          无参，开放权限
 * authc	      无参，需要认证
 * authcBasic     无参，表示httpBasic认证
 * perms[user]	  参数可写多个，表示需要某个或某些权限才能通过，多个参数时写 perms["user, admin"]，当有多个参数时必须每个参数都通过才算通过
 * port[8081]	  当请求的URL端口不是8081时，跳转到schemal://serverName:8081?queryString 其中 schmal 是协议 http 或 https 等等，
 *                  serverName 是你访问的 Host，8081 是 Port 端口，queryString 是你访问的 URL 里的 ? 后面的参数
 * rest[user]	  根据请求的方法，相当于 perms[user:method]，其中 method 为 post，get，delete 等
 * roles[admin]	  参数可写多个，表示是某个或某些角色才能通过，多个参数时写 roles["admin，user"]，当有多个参数时必须每个参数都通过才算通过
 * ssl	          无参，表示安全的URL请求，协议为 https
 * user	          无参，表示必须存在用户，当登入操作时不做检查
 * logout         无参，注销，执行后会直接跳转到shiroFilterFactoryBean.setLoginUrl(); 设置的 url  退出的时候使用
 *
 * 常用的主要就是 anon，authc，user，roles，perms 等；
 *
 * anon, authc, authcBasic, user 是第一组认证过滤器，perms, port, rest, roles, ssl 是第二组授权过滤器，
 * 要通过授权过滤器，就先要完成登陆认证操作（即先要完成认证才能前去寻找授权)，才能走第二组授权器
 * （例如访问需要 roles 权限的 url，如果还没有登陆的话，会直接跳转到 shiroFilterFactoryBean.setLoginUrl(); 设置的 url ）
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //开放登陆接口
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/userInfoAdd","perms[userInfo:add]");
        filterChainDefinitionMap.put("/userInfoDel","perms[userInfo:del]");
        filterChainDefinitionMap.put("/userInfo","perms[userInfo:view]");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 设置realm.
        defaultWebSecurityManager.setRealm(customRealm());
        return defaultWebSecurityManager;
    }
    /**
     * 自定义身份认证 realm;
     * 必须写这个类，并加上 @Bean 注解，目的是注入 MyShiroRelam，
     * 否则会影响 MyShiroRelam 中其他类的依赖注入
     */
    @Bean
    public MyShiroRelam customRealm() {
        return new MyShiroRelam();
    }

    /**
     * 配置ShiroDialect,用于thymeleaf和shiro标签配合使用
     * @return
     */
    @Bean("shiroDialect")
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
