package com.qhit.itravel.config;

import com.qhit.itravel.entity.Role;
import com.qhit.itravel.entity.SysPermission;
import com.qhit.itravel.entity.SysUser;
import com.qhit.itravel.service.SysPermissionService;
import com.qhit.itravel.service.SysUserService;
import com.qhit.itravel.utils.UserUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser user = UserUtil.getCurrentUser();
        //获取当前用户角色信息
        List<Role> roles = sysUserService.findUserRole(user.getId());

        //权限信息
        HashSet<String> roleSet = new HashSet<>();
        for (Role role : roles) {
            roleSet.add(role.getName());
        }

        authorizationInfo.setRoles(roleSet);

        //用户权限信息
        List<SysPermission> permissions = sysPermissionService.findPermissionsUserId(user.getId());

        Set<String> permissionsSet = new HashSet<>();
        for (SysPermission permission : permissions) {
            if (permission.getPermission()!=null && permission.getPermission()!=""){
                permissionsSet.add(permission.getPermission());
            }
        }

        authorizationInfo.setStringPermissions(permissionsSet);

        //把当前用户的权限放到session里
        UserUtil.setPermissionSession(permissions);


        return authorizationInfo;
    }


    //认证 登陆功能
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        String username = usernamePasswordToken.getUsername();

        SysUser user = sysUserService.getUser(username);
        if (user == null) {
            throw new UnknownAccountException("用户名不存在");
        }

        if (!user.getPassword()
                .equals(sysUserService.passwordEncoder(new String(usernamePasswordToken.getPassword()), user.getSalt()))) {
            throw new IncorrectCredentialsException("密码错误");
        }


        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), getName());

        UserUtil.setUserSession(user);

        return authenticationInfo;
    }
}
