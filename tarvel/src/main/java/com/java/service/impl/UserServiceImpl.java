package com.java.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.entity.*;
import com.java.mapper.UserMapper;
import com.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserName(String username) {
        return userMapper.getUserName(username);
    }

    @Override
    public boolean addUser(User user) {
        return userMapper.addUser(user)>0;
    }

    @Override
    public User getUserNamePass(String username, String password) {
        return userMapper.getUserNamePass(username,password);
    }

    @Override
    public List<Category> getCategory() {
        return userMapper.getCategory();
    }

    @Override
    public PageInfo<Route> getPageData(Integer cid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<Route> list = userMapper.getPageData(cid);

        PageInfo<Route> info = new PageInfo<>(list);

        return info;


    }

    @Override
    public Route getPageDataImg(Integer rid) {
        return userMapper.getPageDataImg(rid);
    }

    @Override
    public boolean getRidUid(Favorite favorite) {
        return userMapper.getRidUid(favorite)>0;
    }

    @Override
    public List<Route> getFavorite(Integer uid) {
        return userMapper.getFavorite(uid);
    }

    @Override
    public Favorite getFavoriteId(Integer rid, Integer uid) {

        return userMapper.selectId(rid,uid);
    }

    @Override
    public Integer getCount(Integer rid) {
        return userMapper.selectCount(rid);
    }

    @Override
    public Integer updataCount(Integer rid) {
        return userMapper.update(rid);
    }

    @Override
    public PageInfo<Route> updataCountPaiHang(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<Route> list = userMapper.selectPaiXu();

        PageInfo<Route> info = new PageInfo<>(list);

        return info;
    }

    @Override
    public PageInfo<Route> getPageData1(String rname, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<Route> list = userMapper.getPageData1(rname);

        PageInfo<Route> info = new PageInfo<>(list);

        return info;
    }

    @Override
    public boolean getRidUid2(Integer rid, Integer uid) {
        return userMapper.getRidUid2(rid,uid)>0;
    }

    @Override
    public Integer updataCountqx(Integer rid) {
        return userMapper.updataCountqx(rid);
    }
}
