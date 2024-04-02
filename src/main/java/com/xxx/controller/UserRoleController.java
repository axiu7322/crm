package com.xxx.controller;

import com.xxx.base.BaseController;
import com.xxx.dao.UserRoleMapper;
import com.xxx.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user_role")
public class UserRoleController extends BaseController {
    @Resource
    private UserService userService;
}
