package com.kss.shiro_demo.controller;

import com.kss.shiro_demo.entity.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: shiro_demo
 * @Date: 2019-05-21 9:56
 * @Author: 康仕森
 * @Description:
 */
@Controller
public class UserController {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/403")
    public String error(){
        return "403";
    }
    @RequestMapping("/userInfo")
    public String userInfo(){
        return "userInfo";
    }
    @RequestMapping("/userInfoAdd")
    public String userInfoAdd(){
        return "userInfoAdd";
    }
    @RequestMapping("/userInfoDel")
    public String userInfoDel(){
        return "userInfoDel";
    }
    @RequestMapping("/login")
    public String login(UserInfo userInfo, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userInfo.getUsername(),userInfo.getPassword());
        try {
            subject.login(usernamePasswordToken);
            return "index";
        } catch (UnknownAccountException e) {  //UnknownAccountException异常代表用户名错误
            model.addAttribute("msg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e) { //IncorrectCredentialsException异常代表密码错误
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }
}
