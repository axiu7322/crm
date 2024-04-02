package com.xxx.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.base.BaseService;
import com.xxx.dao.CustomerMapper;
import com.xxx.query.CustomerQuery;
import com.xxx.query.SaleChanceQuery;
import com.xxx.vo.Customer;
import com.xxx.vo.SaleChance;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerService extends BaseService <Customer,Integer>  {

    @Resource
    private CustomerMapper customerMapper;

    public Map<String, Object> queryCustomerByParams(CustomerQuery customerQuery) {
        Map<String,Object> map = new HashMap<>();

        //开启分页
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getLimit());
        //得到对应分页对象
        PageInfo<Customer> pageInfo = new PageInfo<>(customerMapper.selectByParams(customerQuery));
        System.out.println(pageInfo);
        //给map赋值
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        //设置好分页列表
        map.put("data",pageInfo.getList());

        return map;
    }



}
