package com.xxx.service;

import com.xxx.base.BaseService;
import com.xxx.dao.ModuleMapper;
import com.xxx.dao.PermissionMapper;
import com.xxx.dao.RoleMapper;
import com.xxx.utils.AssertUtil;
import com.xxx.vo.Permission;
import com.xxx.vo.Role;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private ModuleMapper moduleMapper;

    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleMapper.queryAllRoles(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addRole(Role role) {
        //参数校验
        checkRole(role.getRoleName(),role.getRoleRemark());
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.insertSelective(role) !=1,"角色添加失败");
    }

    private void checkRole(String roleName, String roleRemark) {
        AssertUtil.isTrue(StringUtils.isBlank(roleName),"角色名不能为空!!!");
        Role temp = roleMapper.selectByRoleName(roleName);
        AssertUtil.isTrue(temp != null,"角色已存在，请重新输入!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        AssertUtil.isTrue(StringUtils.isBlank(roleRemark),"角色备注不能为空!!!");

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRoleIds(Integer roleId) {
        //先判断用户id是否存在
        AssertUtil.isTrue(null == roleId , "待删除记录不存在");
        //删除操作 把值编程
        Role role = roleMapper.selectByPrimaryKey(roleId);
        role.setIsValid(0);
        role.setUpdateDate(new Date());
        //在根据判断操作的长度是否等于1，如果不是1就报异常
        //不出意外就是这个出了问题
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) <1,"角色删除失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role) {
        //1.参数校验
        //角色id  非空 且数据存在
        AssertUtil.isTrue(null == role.getId(),"待更新记录不存在");
        //通过角色id查询角色记录
        Role temp = roleMapper.selectByPrimaryKey(role.getId());
        //判断角色记录是否存在
        AssertUtil.isTrue(null==temp,"待更新记录不存在");
        //角色名称 非空，名称唯一
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名称不能为空");
        //通过角色名称查询角色记录
        temp=roleMapper.selectByRoleName(role.getRoleName());
        //判断角色记录是否存在  不存在：表示可用  存在，且角色id与当前更新的id不一致：表示不可用
        AssertUtil.isTrue(null!=temp&&(!temp.getId().equals(role.getId())),"角色名称已存在，不可使用");
        //2.设置参数的默认值
        role.setUpdateDate(new Date());
        //3.执行更新操作 判断受影响的行数
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"角色修改失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer roleId, Integer[] mIds) {
        Integer count = permissionMapper.countPermissionByRoleId(roleId);
        if (count > 0 ){
            permissionMapper.deletePermissionByRoleId(roleId);
        }
        if (mIds != null && mIds.length >0){
            List<Permission> permissionsList = new ArrayList<>();
            for (Integer mid:mIds) {
                Permission permission = new Permission();
                permission.setModuleId(mid);
                permission.setRoleId(roleId);
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permissionsList.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.insertBatch(permissionsList) != permissionsList.size(),"角色授权失败");
        }
    }
}
