package com.xxx.dao;

import com.xxx.base.BaseMapper;
import com.xxx.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {


    public int  countPermissionByRoleId(Integer roleId);

    public int  deletePermissionByRoleId(Integer roleId);

    public List<Integer> queryRoleHasAllMids(Integer roleId);

    List<Integer>  queryUserHasRoleIdsHasModuleIds(Integer userId);
    List<Integer> queryRoleHasModuleIdsByRoleId(Integer roleId);
    Integer  countPermissionByModuleId(Integer mid);

    int  deletePermissionByModuleId(Integer mid);

    List<String> queryUserHasRoleIdsPermissionByUserId(Integer userId);
}