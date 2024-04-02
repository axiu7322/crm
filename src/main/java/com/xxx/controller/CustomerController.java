package com.xxx.controller;


import com.xxx.query.CustomerQuery;
import com.xxx.service.CustomerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;


    @RequestMapping("index")
    public String index() {
        return "customer/customer";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerByParams(CustomerQuery customerQuery) {
         return customerService.queryCustomerByParams(customerQuery);
    }
}
