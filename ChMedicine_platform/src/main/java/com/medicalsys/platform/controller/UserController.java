package com.medicalsys.platform.controller;

import com.medicalsys.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 司振阳
 */ /*
用户响应层
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    //登录
    @PostMapping("/loginpost")
    @ResponseBody
    public String login(String account, String password, HttpServletRequest request){
        return userService.login(account, password, request.getSession());
    }

    //注册
    @PostMapping("/registpost")
    @ResponseBody
    public String regist(String account,String password){
       return userService.insertUser(account, password);
    }

    //找回密码
    @PostMapping("/forgetpost")
    @ResponseBody
    public String forget(String account,String password){
        return userService.updatePass(account, password);
    }


    //更新用户信息
    @PostMapping("/updateuserinfo")
    @ResponseBody
    public String updateUserInfo(String username,String oldpassword,String password,HttpServletRequest request){
        return userService.updateUser(username, oldpassword,password, request.getSession());
    }
}
