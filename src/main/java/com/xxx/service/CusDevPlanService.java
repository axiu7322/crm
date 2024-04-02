package com.xxx.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.base.BaseService;
import com.xxx.dao.CusDevPlanMapper;
import com.xxx.dao.SaleChanceMapper;
import com.xxx.query.CusDevPlanQuery;
import com.xxx.query.SaleChanceQuery;
import com.xxx.utils.AssertUtil;
import com.xxx.vo.CusDevPlan;
import com.xxx.vo.SaleChance;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {

    @Resource
    private CusDevPlanMapper cusDevPlanMapper;
    @Resource
    private SaleChanceMapper saleChanceMapper;

    public Map<String, Object> queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery) {
        Map<String,Object> map = new HashMap<>();

        //开启分页
        PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getLimit());
        //得到对应分页对象
        PageInfo<CusDevPlan> pageInfo = new PageInfo<>(cusDevPlanMapper.selectByParams(cusDevPlanQuery));
        //给map赋值
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        //设置好分页列表
        map.put("data",pageInfo.getList());

        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addCusDevPlan(CusDevPlan cusDevPlan){
        checkCusDevPlanParams(cusDevPlan);
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan) != 1 ,"计划数据添加失败");
    }

    private void checkCusDevPlanParams(CusDevPlan cusDevPlan) {
        Integer sid = cusDevPlan.getSaleChanceId();
        AssertUtil.isTrue(null ==sid ||saleChanceMapper.selectByPrimaryKey(sid) ==null,"数据异常，请重试" );
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"计划内容不能为空!!");
        AssertUtil.isTrue(null ==cusDevPlan.getPlanDate(),"计划时间不能为空");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCusDevPlan(CusDevPlan cusDevPlan) {
        AssertUtil.isTrue(cusDevPlan.getId() == null ||
                cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getSaleChanceId()) ==null, "程序更新失败");
        checkCusDevPlanParams(cusDevPlan);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan) !=1,"业务更新失败");
    }

    public void deleteCusDevPlan(Integer id) {
        AssertUtil.isTrue(id== null ,"待删除信息不存在" );
        CusDevPlan cusDevPlan = cusDevPlanMapper.selectByPrimaryKey(id);
        cusDevPlan.setIsValid(0);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan) != 1,"计划项数据删除失败");
    }
}
