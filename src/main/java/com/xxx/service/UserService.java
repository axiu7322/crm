package com.xxx.service;

import com.xxx.base.BaseService;
import com.xxx.dao.UserMapper;
import com.xxx.dao.UserRoleMapper;
import com.xxx.model.UserModel;
import com.xxx.utils.AssertUtil;
import com.xxx.utils.Md5Util;
import com.xxx.utils.PhoneUtil;
import com.xxx.utils.UserIDBase64;
import com.xxx.vo.User;
import com.xxx.vo.UserRole;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User, Integer> {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    public UserModel userLogin(String userName,String userPwd){
        checkLoginParams(userName,userPwd);
        User user = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(user==null,"用户姓名不存在!");
        cherUserPwd(userPwd,user.getUserPwd());
        return buildUserInfo(user);
    }


        @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassWord(Integer userId,String oldPwd,String newPwd,String repeatPwd){
        User user = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(user == null,"待更新记录不存在");
        checkPassWordParams(user,oldPwd,newPwd,repeatPwd);
        user.setUserPwd(Md5Util.encode(newPwd));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"修改密码失败");

        }

    private void checkPassWordParams(User user, String oldPwd, String newPwd, String repeatPwd) {
        //判断密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd),"原始密码不能为空！");
        //判断原始密码是否正确（查询用户对象中的用户密码是否与原始密码一致）
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)),"原始密码不正确！");
        //判断新密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(newPwd),"新密码不能为空！");
        //判断新密码是否与原始密码一致
        AssertUtil.isTrue(oldPwd.equals(newPwd),"新密码不能与原始密码相同！");
        //判断确认密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd),"确认密码不能为空！");
        //判断确认密码是否与新密码一致
        AssertUtil.isTrue(!newPwd.equals(repeatPwd),"确认密码与新密码不一致！");
    }


    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
//        userModel.setId(user.getId());
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    /**
     * 判断密码
     * 先md5加密密码，再去判断是否跟数据库里的密码一至
     * @param userPwd
     * @param pwd
     */
    private void cherUserPwd(String userPwd,String pwd){
        userPwd = Md5Util.encode(userPwd);
        AssertUtil.isTrue(!userPwd.equals(pwd),"用户密码不正确");
    }

    /**
     * 判断账号密码不能为空
     * @param userName
     * @param userPwd
     */
    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户姓名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不能为空!");
    }

    public List<Map<String,Object>> queryAllSales(){
        return userMapper.queryAllSales();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void add(User user){
        cherUserParams(user.getUserName(),user.getEmail(),user.getPhone(),null);
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(userMapper.insertSelective(user) < 1 , "添加用户失败");
        relationUserRole(user.getId(),user.getRoleIds());
    }

    private void relationUserRole(Integer userId, String roleIds) {
        Integer count = userRoleMapper.countUserRoleByUserId(userId);
        if (count >0){
           AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count,"用户角色分配失败");
        }

        if (StringUtils.isNotBlank(roleIds)) {
            List<UserRole> userRolesList = new ArrayList();
            String[] roleIdsArray = roleIds.split(",");
            for (String roleId : roleIdsArray) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setUserId(userId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRolesList.add(userRole);
            }

            AssertUtil.isTrue(userRoleMapper.insertBatch(userRolesList) != userRolesList.size(),"用户角色分配失败");
            }
        }

    private void cherUserParams(String userName, String email, String phone,Integer userId) {
        AssertUtil.isTrue(StringUtils.isBlank(userName) ,"用户名不能为空!");
        // todo 我觉得这一行有问题 通过名字查询? 如果有同名的两个不同人 怎么处理? 先假设这个逻辑没问题 继续看
        // 这个先不说， 但是我感觉我逻辑没问题，好像我这种是麻烦一点，效果一样，我先把我的删了，我运行一下
        User temp = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(userId)), "用户名已存在，请重新输入");//我同桌是这个东西，有一点不一样
        //AssertUtil.isTrue( temp != null && !(userId.equals(userMapper.selectByPrimaryKey(userId))), "用户名已存在");//他没有进行判断这个
        AssertUtil.isTrue(StringUtils.isBlank(email)  ,"邮箱不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(phone)  ,"手机号不能为空!");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号格式不正确");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser( User user){
        AssertUtil.isTrue(null== user.getId(),"待更新记录不存在");
        User temp = userMapper.selectByPrimaryKey(user.getId());
        AssertUtil.isTrue( null == temp , "待更新记录不存在");
        cherUserParams(user.getUserName(),user.getEmail(),user.getPhone(),user.getId());
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) != 1 , "更新用户失败");
        relationUserRole(user.getId(),user.getRoleIds());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserByIds(Integer[] ids) {
        AssertUtil.isTrue(null==ids || ids.length==0,"请选择待删除的用户记录!");
        AssertUtil.isTrue(deleteBatch(ids)!=ids.length,"用户记录删除失败!");

        for (Integer id : ids) {
            Integer count = userRoleMapper.countUserRoleByUserId(id);
            if (count > 0){
                //我明明是整数型，为什么还是报错了
                AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(id) != count, "用户删除失败");
            }
        }
    }
}
