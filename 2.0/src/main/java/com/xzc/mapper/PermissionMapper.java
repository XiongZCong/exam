package com.xzc.mapper;

import com.xzc.model.Permission;
import com.xzc.model.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionMapper {

    @Insert("INSERT INTO role_permission(roleId,permissionId) VALUES(#{roleId},#{permissionId}")
    Long insertRolePermission(Long roleId, Long permissionId);

    @Delete("DELETE FROM user_role WHERE roleId=#{roleId}")
    Long deleteUserRole(Long roleId);

    @Delete("DELETE FROM role_permission WHERE roleId=#{roleId}")
    Long deleteRolePermissionByRoleId(Long roleId);

    @Delete("DELETE FROM role_permission WHERE permissionId=#{permissionId}")
    Long deleteRolePermissionByPermissionId(Long permissionId);

    Long insertRole(Role role);

    @Delete("DELETE FROM t_role WHERE roleId=#{roleId} AND operation=true")
    Long deleteRole(Long roleId);

    @Select("select * from t_role")
    List<Role> selectRoles();

    Long udateRole(Role role);

    Long insertPermission(Permission permission);

    Long deletePermission(Permission permission);

    @Select("select * from t_role where roleId=#{roleId}")
    Role selectRoleById(@Param("roleId") Long roleId);

    @Select("select * from t_role where roleName like '%${roleName}%'")
    List<Role> selectRolesByName(@Param("roleName") String roleName);

    @Select("select * from t_permission where permissionName like '%${permissionName}%'")
    List<Permission> selectPermissionsByName(@Param("permissionName") String permissionName);

    Long updatePermissions(Permission permission);

    List selectPermissionsByRoleId(Long roleId);

    @Select("select resource from t_permission")
    List<String> selectResource();
}
