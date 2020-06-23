package com.qhit.itravel.controller;

import com.qhit.itravel.dto.UserDto;
import com.qhit.itravel.entity.SysUser;
import com.qhit.itravel.service.SysUserService;
import com.qhit.itravel.utils.page.PageTableHandler;
import com.qhit.itravel.utils.page.PageTableRequest;
import com.qhit.itravel.utils.page.PageTableResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2020-03-11 10:16:31
 */
@RestController
@RequestMapping("/sys")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    @Autowired
    private ServerProperties serverProperties;

    @RequestMapping("/login")
    public void login(String username,String password){
        //将前台传过来的用户名和密码封装成shiro登陆需要的口令
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        SecurityUtils.getSubject().login(usernamePasswordToken);

        // 设置shiro的session过期时间
        SecurityUtils.getSubject().getSession().setTimeout(serverProperties.getServlet().getSession().getTimeout().toMillis());
    }

    @GetMapping("/users")
    @RequiresPermissions("sys:menu:query")
    //需要权限才能访问
    //@RequiresPermissions("sys:user:query")
    public PageTableResponse getAllUsers(PageTableRequest pageTableRequest){
        PageTableHandler pageTableHandler = new PageTableHandler(new PageCount(),new PageList());
        PageTableResponse pageTableResponse = pageTableHandler.handle(pageTableRequest);
        return pageTableResponse;
    };

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    @RequiresPermissions("sys:menu:query")
    public SysUser selectOne(Integer id) {
        return this.sysUserService.queryById(id);
    }

    class PageCount implements PageTableHandler.CountHandler{
        //private PageTableRequest request;

        @Override
        public int count(PageTableRequest request) {
            int rows = sysUserService.count(request.getParams());
            return rows;
        }
    }

    class PageList implements PageTableHandler.ListHandler{

        @Override
        public List<?> list(PageTableRequest request) {
            Integer offset = request.getOffset();
            Integer limit = request.getLimit();
            List<SysUser> list = sysUserService.getPageData(request.getParams(),offset,limit);
            return list;
        }
    }

    @PostMapping("/add")
    @RequestMapping("sys:user:add")
    public SysUser saveUser(@RequestBody UserDto userDto){
        SysUser user = sysUserService.findAllList(userDto.getUsername());

        if (user!=null){
            throw new IllegalArgumentException(userDto.getUsername()+"该用户已存在");
        }

        return sysUserService.addUser(userDto);
    }

    @PostMapping("/edit")
    public SysUser edit(@RequestBody UserDto userDto){
        return sysUserService.editUser(userDto);
    }


}