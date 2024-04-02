package com.xxx.controller;


import com.xxx.base.BaseController;
import com.xxx.base.ResultInfo;

import com.xxx.model.UserModel;
import com.xxx.query.UserQuery;
import com.xxx.service.UserService;
import com.xxx.utils.LoginUserUtil;
import com.xxx.vo.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
    public ResultInfo usseLogin(String userName, String userPwd){
        ResultInfo resultInfo = new ResultInfo();
        UserModel userModel = userService.userLogin(userName, userPwd);
        resultInfo.setResult(userModel);
//        try {
//            UserModel userModel = userService.userLogin(userName, userPwd);
//            resultInfo.setResult(userModel);
//        }catch (ParamsException p){
//            resultInfo.setCode(p.getCode());
//            resultInfo.setMsg(p.getMsg());
//            p.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();
//            resultInfo.setCode(500);
//            resultInfo.setMsg("登录失败");
//        }

        return resultInfo;
    }

    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassWord(HttpServletRequest request,
                                         String oldPassWord, String newPassWord,
                                         String repeatPassWord){
        ResultInfo resultInfo = new ResultInfo();
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updatePassWord(userId, oldPassWord, newPassWord,repeatPassWord);
//        try {
//            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
//            userService.updatePassWord(userId, oldPassWord, newPassWord,repeatPassWord);
//
//        }catch (ParamsException p){
//            resultInfo.setCode(p.getCode());
//            resultInfo.setMsg(p.getMsg());
//            p.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();
//            resultInfo.setCode(500);
//            resultInfo.setMsg("修改密码失败");
//        }

        return resultInfo;
    }

    @RequestMapping("toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }


    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String,Object>> queryAllSales(){
        return userService.queryAllSales();
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> selectByParams(UserQuery userQuery){
        return userService.queryByParamsForTable(userQuery);
    }

    @RequestMapping("index")
    public String index(){
        return "user/user";
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo addUser(User user){
        userService.add(user);
        // System.out.println(user);
        return success("添加用户成功");
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("更新用户成功");
    }

    @RequestMapping("addOrUpdateUserPage")
    public String addOrUpdateUserPage(Integer id,HttpServletRequest request){
        if (id != null){
           User user = userService.selectByPrimaryKey(id);
            System.out.println(user);
           request.setAttribute("userInfo", user);
        }
    return "user/add_update";
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteUserByIds(ids);
        return success("删除成功");
    }


}
