package com.qhit.itravel.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.google.common.collect.Lists;
import com.qhit.itravel.entity.SysPermission;
import com.qhit.itravel.entity.SysUser;
import com.qhit.itravel.service.SysPermissionService;
import com.qhit.itravel.utils.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (SysPermission)表控制层
 *
 * @author makejava
 * @since 2020-03-12 16:26:37
 */
@RestController
@RequestMapping("/permissions")
public class SysPermissionController {
    /**
     * 服务对象
     */
    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysPermission selectOne(Integer id) {
        return this.sysPermissionService.queryById(id);
    }

    @PostMapping("/current")
    public List<SysPermission> getPermissionCurrent(){
        List<SysPermission> list = UserUtil.getCurrentPermissions();
        if (list==null){
            SysUser currentUser = UserUtil.getCurrentUser();
            list = sysPermissionService.findPermissionsUserId(currentUser.getId());
            UserUtil.setPermissionSession(list);
        }

        //获取type=1的菜单
        List<SysPermission> permissionList1 = new ArrayList<>();
        List<SysPermission> permissionList2 = new ArrayList<>();

        for (SysPermission permission : list) {
            if (permission.getType()==(Object)1){
                permissionList1.add(permission);
            }

            if (permission.getParentid()==0){
                permissionList2.add(permission);
            }
        }

        for (SysPermission sysPermission : permissionList2) {
            setChildren(sysPermission,permissionList1);
        }

        return permissionList2;



    }

    private void setChildren(SysPermission p, List<SysPermission> permissions) {
        List<SysPermission> children = new ArrayList<>();
        for (SysPermission permission : permissions) {
            if (p.getId() == permission.getParentid()){
                children.add(permission);
            }
        }

        p.setChild(children);

        //递归处理
        if (children!=null && children.size()>0){
            for (SysPermission child : children) {
                setChildren(child,permissions);
            }
        }
    }

    @GetMapping("/owns")

    public Set<String> ownsPermission() {
        List<SysPermission> permissions = UserUtil.getCurrentPermissions();
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptySet();
        }

        return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                .map(SysPermission::getPermission).collect(Collectors.toSet());
    }

    @GetMapping("/all")
    @RequiresPermissions("sys:menu:query")
    public JSONArray listAll(){
        List<SysPermission> allPermissions = sysPermissionService.getAll();
        JSONArray array = new JSONArray();
        buildTreePermissions(0,allPermissions,array);

        return array;
    }

    private void buildTreePermissions(int pId, List<SysPermission> allPermissions, JSONArray array) {
        for (SysPermission permission : allPermissions) {
            if (permission.getParentid()==pId){
                String json = JSONObject.toJSONString(permission);
                JSONObject parent  = (JSONObject)JSONObject.parse(json);
                array.add(parent);
                for (SysPermission p : allPermissions) {
                    if (permission.getParentid() == p.getParentid()){
                        JSONArray children = new JSONArray();
                        parent.put("child",children);
                        buildTreePermissions(permission.getId(),allPermissions,children);
                    }
                }
            }
        }
    }

    //递归过程
    private void setPermissionsList(int pId, List<SysPermission> permissionsAll, List<SysPermission> list) {
        for (SysPermission per : permissionsAll) {
            if (per.getParentid().equals(pId)) {
                list.add(per);
                if (permissionsAll.stream().filter(p -> p.getParentid().equals(per.getId())).findAny() != null) {
                    setPermissionsList(per.getId(), permissionsAll, list);
                }
            }
        }
    }

    @GetMapping("allType1Menus")
    @RequiresPermissions("sys:menu:query")
    public List<SysPermission> getAllType1Menus(){
        List<SysPermission> permissionsAll = sysPermissionService.queryAll();
        List<SysPermission> list = Lists.newArrayList();
        setPermissionsList(0,permissionsAll,list);
        return list;
    }

    @GetMapping("/parents")
    @RequiresPermissions("sys:menu:query")
    public List<SysPermission> getParentsMenus(){
        return sysPermissionService.getParentsMenus();
    }

    @PostMapping
    @RequiresPermissions("sys:menu:add")
    public void addMenu(@RequestBody SysPermission sysPermission){
        sysPermissionService.insert(sysPermission);
    }

    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:menu:delete")
    public void deleteMenu(@PathVariable Integer id){
        sysPermissionService.deleteById(id);
    }

    @GetMapping("/{id}")
    @RequiresPermissions("sys:menu:query")
    public SysPermission getMenuById(@PathVariable Integer id){
        return sysPermissionService.queryById(id);
    }

    @PutMapping
    @RequiresPermissions("sys:menu:add")
    public void updateMenu(@RequestBody SysPermission sysPermission){
        sysPermissionService.update(sysPermission);
    }

}