package com.qhit.itravel.entity;

import java.io.Serializable;

/**
 * (Category)实体类
 *
 * @author makejava
 * @since 2020-04-10 15:38:56
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 348082191436937456L;
    
    private Integer cid;
    
    private String cname;


    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

}