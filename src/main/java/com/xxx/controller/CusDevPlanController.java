package com.xxx.controller;

import com.xxx.base.BaseController;
import com.xxx.base.ResultInfo;
import com.xxx.query.CusDevPlanQuery;
import com.xxx.service.CusDevPlanService;
import com.xxx.service.SaleChanceService;
import com.xxx.vo.CusDevPlan;
import com.xxx.vo.SaleChance;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {


    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private CusDevPlanService cusDevPlanService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery) {
        return cusDevPlanService.queryCusDevPlanByParams(cusDevPlanQuery);
    }

    @RequestMapping("index")
    public String index() {
        return "cusDevPlan/cus_dev_plan";
    }

    @RequestMapping("toCusDevPlanPage")
    public String toCusDevPlanPage(Integer id, HttpServletRequest request) {

        SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
        request.setAttribute("saleChance", saleChance);
        return "cusDevPlan/cus_dev_plan_data";
    }

    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addCusDevPlan(CusDevPlan cusDevPlan) {
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        return success("数据添加成功!!");
    }

    @RequestMapping("addOrUpdateCusDevPlanPage")
    public String addOrUpdateCusDevPlanPage(HttpServletRequest request,Integer sId,Integer id){
        request.setAttribute("sId", sId);
        CusDevPlan cusDevPlan = cusDevPlanService.selectByPrimaryKey(id);
        request.setAttribute("cusDevPlan", cusDevPlan);
        return "cusDevPlan/add_update";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("计划更新成功");
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteCusDevPlan(Integer id){
        cusDevPlanService.deleteCusDevPlan(id);
        return success("计划删除成功");
    }
}
