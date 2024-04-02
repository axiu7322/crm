package com.xxx.dao;

import com.xxx.base.BaseMapper;
import com.xxx.vo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {

     User queryUserByName(String userName);

     List<Map<String,Object>> queryAllSales();
}
