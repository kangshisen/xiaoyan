package com.kss.shiro_demo.dao;

import com.kss.shiro_demo.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @program: shiro_demo
 * @Date: 2019-05-21 14:47
 * @Author: 康仕森
 * @Description:
 */
public interface UserDao extends CrudRepository<UserInfo,Integer> {
    UserInfo findByUsername(String username);
}
