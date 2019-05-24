package com.kss.shiro_demo.service;

import com.kss.shiro_demo.entity.UserInfo;

/**
 * @program: shiro_demo
 * @Date: 2019-05-21 14:51
 * @Author: 康仕森
 * @Description:
 */
public interface UserService {
    UserInfo findByUsername(String username);
}
