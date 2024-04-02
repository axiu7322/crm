package com.xxx.dao;

import com.xxx.base.BaseMapper;
import com.xxx.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

    List<Map<String,Object>> queryAllRoles(Integer userId);

    Role selectByRoleName(String roleName);

}
