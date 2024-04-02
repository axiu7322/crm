package com.xxx.controller;

import com.xxx.base.BaseController;
import com.xxx.service.PermissionService;
import com.xxx.service.UserService;
import com.xxx.utils.LoginUserUtil;
import com.xxx.vo.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;


@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    //系统登录页
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    //系统页面欢迎页
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    //后端管理主页
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.selectByPrimaryKey(userId);
//        request.getSession().setAttribute("user",user);
        request.setAttribute("user",user);
        List<String> permissions = permissionService.queryUserHasRoleIdsPermissionByUserId(userId);
        request.setAttribute("permissions",permissions);
        System.out.println(permissions);
        return "main";
    }
}
