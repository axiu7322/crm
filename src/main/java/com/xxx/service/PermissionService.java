package com.xxx.service;

import com.xxx.base.BaseService;
import com.xxx.dao.PermissionMapper;
import com.xxx.vo.Permission;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Resource
    private PermissionMapper permissionMapper;


    public List<String> queryUserHasRoleIdsPermissionByUserId(Integer userId) {
        return permissionMapper.queryUserHasRoleIdsPermissionByUserId(userId);
    }
}
