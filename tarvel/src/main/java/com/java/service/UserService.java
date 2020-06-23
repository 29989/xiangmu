package com.java.service;

import com.github.pagehelper.PageInfo;
import com.java.entity.*;

import java.util.List;

public interface UserService {
    User getUserName(String username);

    boolean addUser(User user);

    User getUserNamePass(String username, String password);

    List<Category> getCategory();

    PageInfo<Route> getPageData(Integer cid, Integer pageNum, Integer pageSize);

    Route getPageDataImg(Integer rid);


    boolean getRidUid(Favorite favorite);

    List<Route> getFavorite(Integer uid);

    Favorite getFavoriteId(Integer rid, Integer uid);

    Integer getCount(Integer rid);

    Integer updataCount(Integer rid);

    PageInfo<Route> updataCountPaiHang(Integer pageNum, Integer pageSize);

    PageInfo<Route> getPageData1(String rname, Integer pageNum, Integer pageSize);

    boolean getRidUid2(Integer rid, Integer uid);

    Integer updataCountqx(Integer rid);
}
