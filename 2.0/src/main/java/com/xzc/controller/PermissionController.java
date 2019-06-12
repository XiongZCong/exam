package com.xzc.controller;

import com.xzc.model.Permission;
import com.xzc.model.Role;
import com.xzc.result.Result;
import com.xzc.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @RequestMapping("/permission/reloadPermission")
    public void reloadPermission() {
        System.out.println("PermissionController.reloadPermission");
        permissionService.reloadPermission();
    }

    @RequestMapping("/permission/selectRoles")
    public Result selectRoles() {
        return Result.success(permissionService.selectRoles());
    }

    @RequestMapping("/permission/selectRoleById")
    public Result selectRoleById(Long roleId) {
        return Result.success(permissionService.selectRoleById(roleId));
    }

    @RequestMapping("/permission/selectRolesByName")
    public Result selectRolesByName(String roleName, int pageNum, int pageSize) {
        return Result.success(permissionService.selectRolesByName(roleName, pageNum, pageSize));
    }

    @RequestMapping("/permission/selectPermissionsByName")
    public Result selectPermissionsByName(String permissionName, int pageNum, int pageSize) {
        return Result.success(permissionService.selectPermissionsByName(permissionName, pageNum, pageSize));
    }

    @RequestMapping("/permission/selectPermissionsByRoleId")
    public Result selectPermissionsByRoleId(Long roleId) {
        return Result.success(permissionService.selectPermissionsByRoleId(roleId));
    }

    @RequestMapping("/permission/insertRole")
    public Result insertRole(Role role) {
        return Result.success(permissionService.insertRole(role));
    }

    @RequestMapping("/permission/insertPermission")
    public Result insertPermission(Permission permission) {
        return Result.success(permissionService.insertPermission(permission));
    }

}
