package com.medicalsys.platform.service;

import javax.servlet.http.HttpSession;

/*
用户服务层接口
 */
public interface UserService {

    //登录
    public String login(String account, String password, HttpSession session);

    //更新用户信息
    public String updateUser(String username,String oldPassword,String password,HttpSession session);

    //注册
    public String insertUser(String account,String password);


    //忘记密码
    public String updatePass(String account,String password);
}
