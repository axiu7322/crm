package com.xxx.controller;

import com.xxx.base.BaseController;
import com.xxx.base.ResultInfo;
import com.xxx.model.TreeModel;
import com.xxx.service.ModuleService;
import com.xxx.vo.Module;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController{

    @Resource
    private ModuleService moduleService;

    @RequestMapping("index")
    public String index(){
        return "module/module";
    }

    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeModel> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }

    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, HttpServletRequest request) {
        //将需要授权的角色id设置到请求域中
        request.setAttribute("roleId", roleId);
        return "role/grant";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryModuleList(){
        return moduleService.queryModuleList();
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo addModule(Module module){
        moduleService.addModule(module);
        return success("添加资源成功");
    }


    @RequestMapping("addModulePage")
    public String toAddModulePage(Integer grade,Integer parentId,HttpServletRequest request) {
        request.setAttribute("grade", grade);
        request.setAttribute("parentId", parentId);
        return "module/add";
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateModule(Module module){
        moduleService.updateModule(module);
        return success("修改资源成功");
    }

    @RequestMapping("updateModulePage")
    public String updateModulePage(Integer id, Model model){
        model.addAttribute("module",moduleService.selectByPrimaryKey(id));
        return "module/update";
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteModule(Integer id){
        moduleService.deleteModule(id);
        return success("删除资源成功");
    }
}
