package com.xxx.dao;

import com.xxx.base.BaseMapper;
import com.xxx.vo.UserRole;


public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    Integer countUserRoleByUserId(Integer userId);

    Integer deleteUserRoleByUserId(Integer userId);
}
