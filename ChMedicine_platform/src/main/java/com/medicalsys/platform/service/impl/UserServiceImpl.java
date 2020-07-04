package com.medicalsys.platform.service.impl;

import com.medicalsys.platform.bean.User;
import com.medicalsys.platform.dao.UserDao;
import com.medicalsys.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;


/*
用户服务层
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    //登录
    @Override
    public String login(String account, String password, HttpSession session) {
        User user = userDao.findUser(account);
        if(user!=null){
            if(user.getPassword().equals(password)){
            session.setAttribute("user", user);
                return "{\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"登录成功\",\n" +
                        "  \"data\": {\n" +
                        "  }\n" +
                        "}";
            }
            return "{\n" +
                    "  \"code\": 1,\n" +
                    "  \"msg\": \"密码错误\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }else {
            return "{\n" +
                    "  \"code\": 1,\n" +
                    "  \"msg\": \"用户不存在\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }
    }

    //更新用户信息
    @Override
    public String updateUser(String username, String oldPassword,String password,HttpSession session) {
        User user= (User) session.getAttribute("user");
        if(user.getPassword().equals(oldPassword)){
        int updateFlag = userDao.updateUser(username, password,user.getId());
        if(updateFlag==1){
            user.setUsername(username);
            user.setPassword(password);
            session.setAttribute("user", user);
            return "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"msg\": \"更新成功\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }

        return "{\n" +
                "  \"code\": 1,\n" +
                "  \"msg\": \"更新失败\",\n" +
                "  \"data\": {\n" +
                "  }\n" +
                "}";
        }

        return "{\n" +
                "  \"code\": 1,\n" +
                "  \"msg\": \"原密码错误\",\n" +
                "  \"data\": {\n" +
                "  }\n" +
                "}";
    }

    @Override
    public String insertUser(String account, String password) {
        User user=userDao.findUser(account);
        if(user!=null){
            return "{\n" +
                    "  \"code\": 1,\n" +
                    "  \"msg\": \"账号已经被注册\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }else {
            int flag = userDao.insertUser(account, password);
            if(flag==1){
                return "{\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"注册成功\",\n" +
                        "  \"data\": {\n" +
                        "  }\n" +
                        "}";
            }else{
                return "{\n" +
                        "  \"code\": 1,\n" +
                        "  \"msg\": \"注册失败\",\n" +
                        "  \"data\": {\n" +
                        "  }\n" +
                        "}";
            }
        }
    }

    @Override
    public String updatePass(String account, String password) {
        int updateFlag = userDao.updatePass(account, password);
        if(updateFlag==1){
            return "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"msg\": \"重置成功\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }

        return "{\n" +
                "  \"code\": 1,\n" +
                "  \"msg\": \"重置失败(账号不存在)\",\n" +
                "  \"data\": {\n" +
                "  }\n" +
                "}";

    }
}
