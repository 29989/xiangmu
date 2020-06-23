package com.qhit.itravel.entity;

import java.io.Serializable;
import java.util.List;

/**
 * (SysPermission)实体类
 *
 * @author makejava
 * @since 2020-03-12 16:26:35
 */
public class SysPermission implements Serializable {
    private static final long serialVersionUID = -20087872762226749L;
    
    private Integer id;
    
    private Integer parentid;
    
    private String name;
    
    private String css;
    
    private String href;
    
    private Object type;
    
    private String permission;
    
    private Integer sort;

    private List<SysPermission> child;

    public List<SysPermission> getChild() {
        return child;
    }

    public void setChild(List<SysPermission> child) {
        this.child = child;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}