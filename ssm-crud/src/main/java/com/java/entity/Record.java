package com.java.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Record)实体类
 *
 * @author makejava
 * @since 2020-04-30 15:21:10
 */
public class Record implements Serializable {
    private static final long serialVersionUID = 571374050861643008L;
    
    private Integer id;
    
    private String person;
    
    private String community;
    
    private String tel;
    
    private String carnum;
    
    private Object isoutcity;
    
    private Object isformhb;
    
    private Object ishousehold;
    
    private Date nowtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public Object getIsoutcity() {
        return isoutcity;
    }

    public void setIsoutcity(Object isoutcity) {
        this.isoutcity = isoutcity;
    }

    public Object getIsformhb() {
        return isformhb;
    }

    public void setIsformhb(Object isformhb) {
        this.isformhb = isformhb;
    }

    public Object getIshousehold() {
        return ishousehold;
    }

    public void setIshousehold(Object ishousehold) {
        this.ishousehold = ishousehold;
    }

    public Date getNowtime() {
        return nowtime;
    }

    public void setNowtime(Date nowtime) {
        this.nowtime = nowtime;
    }

}