package com.java.mapper;

import com.github.pagehelper.PageInfo;
import com.java.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    User getUserName(String username);

    int addUser(User user);

    User getUserNamePass(@Param(value = "username") String username,@Param(value = "password") String password);

    List<Category> getCategory();

    List<Route> getPageData(Integer cid);

    Route getPageDataImg(Integer rid);

    int getRidUid(Favorite favorite);

    List<Route> getFavorite(Integer uid);

    Favorite selectId(@Param("rid") Integer rid,@Param("uid") Integer uid);

    Integer selectCount(Integer rid);

    Integer update(Integer rid);

    List<Route> selectPaiXu();

    List<Route> getPageData1(String rname);


    int getRidUid2(@Param("rid") Integer rid,@Param("uid") Integer uid);

    Integer updataCountqx(Integer rid);
}
