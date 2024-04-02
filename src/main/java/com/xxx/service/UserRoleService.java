package com.xxx.service;

import com.xxx.base.BaseService;
import com.xxx.dao.UserRoleMapper;
import com.xxx.vo.UserRole;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService extends BaseService<UserRole,Integer> {
    @Resource
    private UserRoleMapper userRoleMapper;
}
