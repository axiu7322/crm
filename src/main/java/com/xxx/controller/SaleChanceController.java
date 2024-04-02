package com.xxx.controller;

import com.xxx.annoation.RequiredPermission;
import com.xxx.base.BaseController;
import com.xxx.base.ResultInfo;
import com.xxx.enums.StateStatus;
import com.xxx.query.SaleChanceQuery;
import com.xxx.service.SaleChanceService;
import com.xxx.service.UserService;
import com.xxx.utils.AssertUtil;
import com.xxx.utils.CookieUtil;
import com.xxx.utils.LoginUserUtil;
import com.xxx.vo.SaleChance;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private UserService userService;

    @RequiredPermission(code = "10101")
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery,
                                                       Integer flag,
                                                       HttpServletRequest request) {
        if (flag != null && flag == 1) {
            saleChanceQuery.setState(StateStatus.STATED.getType());
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            saleChanceQuery.setAssignMan(userId);
        }
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }

    @RequiredPermission(code = "1010")
    @RequestMapping("index")
    public String index() {
        return "saleChance/sale_chance";
    }

    @RequiredPermission(code = "101002")
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest request) {

        String userName = CookieUtil.getCookieValue(request, "userName");
        saleChance.setCreateMan(userName);
        saleChanceService.addSaleChance(saleChance);
        return success("营销操作添加成功");
    }

    @RequiredPermission(code = "101004")
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance) {
        saleChanceService.updataSaleChance(saleChance);
        return success("营销操作更新成功");
    }


    @RequestMapping("toSaleChancePage")
    public String toSaleChancePage(Integer saleChanceId, HttpServletRequest request) {
        if (saleChanceId != null) {
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(saleChanceId);
            request.setAttribute("saleChance", saleChance);
        }
        return "saleChance/add_update";
    }


    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(Integer id, Model model) {
        if (null != id) {
            model.addAttribute("saleChance", saleChanceService.selectByPrimaryKey(id));
        }
        return "saleChance/add_update";
    }


    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveSaleChance(HttpServletRequest request, SaleChance saleChance) {
        saleChance.setCreateMan(userService.selectByPrimaryKey(LoginUserUtil.releaseUserIdFromCookie(request)).getTrueName());
        saleChanceService.addSaleChance(saleChance);
        return success("机会数据添加成功");
    }

    @RequiredPermission(code = "101003")
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids) {
        saleChanceService.deleteSaleChance(ids);
        return success("营销机会数据删除成功");

    }

    @PostMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id, Integer devResult) {

        saleChanceService.updateSaleChanceDevResult(id, devResult);
        return success("营销机会开发状态更改成功");
    }
}
