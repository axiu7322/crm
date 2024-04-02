package com.xxx.controller;

import com.xxx.base.BaseController;
import com.xxx.base.ResultInfo;
import com.xxx.query.RoleQuery;
import com.xxx.service.RoleService;
import com.xxx.utils.AssertUtil;
import com.xxx.vo.Role;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(Integer userId){
      return roleService.queryAllRoles(userId);
    }


    @RequestMapping("save")
    @ResponseBody
    public ResultInfo addRole(Role role){
        roleService.addRole(role);
        return success("添加成功");
    }

    @RequestMapping("addOrUpdateRolePage")
    public String addOrUpdateRolePage(Integer id,HttpServletRequest request) {
        if (id != null) {
            Role role = roleService.selectByPrimaryKey(id);
            request.setAttribute("role", role);
        }
        return "role/add_update";
    }
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> selectByParams(RoleQuery roleQuery) {
        return roleService.queryByParamsForTable(roleQuery);
    }

    @RequestMapping("index")
    public String index(){
        return "role/role";
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer roleId){
        roleService.deleteRoleIds(roleId);
        return success("删除成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return success("更新成功");
    }

    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId,HttpServletRequest request){
        request.setAttribute("roleId", roleId);
        return "role/grant";
    }

    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer roleId,Integer[] mIds){
        roleService.addGrant(roleId,mIds);
        return success("授权成功成功");
    }
}
