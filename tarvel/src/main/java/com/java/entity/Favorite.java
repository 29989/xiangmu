package com.java.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 收藏实体类
 */

public class Favorite implements Serializable {
    private int rid;//旅游线路对象
    private Date date;//收藏时间
    private int uid;//所属用户

    private List<Route> route;
    private Category category;//所属分类
    private Seller seller;//所属商家
    private List<RouteImg> routeImgList;//商品详情图片列表

    public Favorite() {
    }

    public Favorite(int rid, Date date, int uid) {
        this.rid = rid;
        this.date = date;
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }



    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * 无参构造方法
     *//*
    public Favorite() {
    }

    *//**
     * 有参构造方法
     * @param route
     * @param date
     * @param user
     *//*
    public Favorite(Route route, String date, User user) {
            this.route = route;
            this.date = date;
            this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/
}
