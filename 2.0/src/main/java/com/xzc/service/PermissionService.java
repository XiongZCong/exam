package com.xzc.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzc.mapper.PermissionMapper;
import com.xzc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Service
@Transactional
public class PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    public List<Role> selectRoles() {
        return permissionMapper.selectRoles();
    }

    public PageInfo<Role> selectRoleById(Long roleId) {
        PageHelper.startPage(1, 10);
        List<Role> roleList = new ArrayList<>();
        roleList.add(permissionMapper.selectRoleById(roleId));
        return new PageInfo<>(roleList);
    }

    public PageInfo<Role> selectRolesByName(String roleName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = permissionMapper.selectRolesByName(roleName);
        return new PageInfo<>(roleList);
    }


    public PageInfo<Permission> selectPermissionsByName(String permissionName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Permission> permissionList = permissionMapper.selectPermissionsByName(permissionName);
        return new PageInfo<>(permissionList);
    }

    public List selectPermissionsByRoleId(Long roleId) {
        return permissionMapper.selectPermissionsByRoleId(roleId);
    }

    public List<String> selectResource() {
        return permissionMapper.selectResource();
    }

    public Long insertRole(Role role) {
        return permissionMapper.insertRole(role);
    }

    public Long insertPermission(Permission permission) {
        return permissionMapper.insertPermission(permission);
    }

    public void reloadPermission() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Collection<HandlerMethod> methods = handlerMethods.values();
        List<String> resources = permissionMapper.selectResource();
        for (HandlerMethod method : methods) {
            RequestMapping anno = method.getMethodAnnotation(RequestMapping.class);
            if (anno != null && anno.value().length > 0) {
                String resource = anno.value()[0];
                String methodName = method.getMethod().getName();
                if (resources.contains(resource)) {
                    continue;
                }
                Permission permission = new Permission();
                permission.setPermissionName(methodName);
                permission.setResource(resource);
                permission.setAccess(String.format("perms[%s]", methodName));
                permission.setCreateTime(new Date());
                permission.setOperation(true);
                permission.setNote(String.format("%s需要perms[%s]权限才能访问", resource, methodName));
                permissionMapper.insertPermission(permission);
            }
        }
    }

    public int deleteRolePermissionByRoleId(List<Long> rolePermission) {
        Long roleId = rolePermission.get(0);
        permissionMapper.deleteRolePermissionByRoleId(roleId);
        int length = rolePermission.size();
        for (int i = 1; i < length; i++) {
            permissionMapper.insertRolePermission(roleId, rolePermission.get(i));
        }
        return length - 1;
    }
}
