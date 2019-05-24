package com.kss.shiro_demo.service.impl;

import com.kss.shiro_demo.dao.UserDao;
import com.kss.shiro_demo.entity.UserInfo;
import com.kss.shiro_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: shiro_demo
 * @Date: 2019-05-21 14:51
 * @Author: 康仕森
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserInfo findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
