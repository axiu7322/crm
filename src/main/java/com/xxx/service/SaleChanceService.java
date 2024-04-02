package com.xxx.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.base.BaseQuery;
import com.xxx.base.BaseService;
import com.xxx.dao.SaleChanceMapper;
import com.xxx.enums.DevResult;
import com.xxx.enums.StateStatus;
import com.xxx.query.SaleChanceQuery;
import com.xxx.utils.AssertUtil;
import com.xxx.utils.PhoneUtil;
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
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery) {
        Map<String,Object> map = new HashMap<>();

        //开启分页
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getLimit());
        //得到对应分页对象
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));
        //给map赋值
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        //设置好分页列表
        map.put("data",pageInfo.getList());

        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance){
        checkSaleChance(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        if (StringUtils.isBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setAssignTime(null);
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        }else {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }
            AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance)!=1,"添加营销机会失败");

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void updataSaleChance(SaleChance saleChance){
        AssertUtil.isTrue(saleChance.getId() == null,"待更新不存在!!!");
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(temp == null,"待更新不存在!!!");
        checkSaleChance(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setUpdateDate(new Date());
        if (StringUtils.isBlank(temp.getAssignMan())){

            if (!StringUtils.isBlank(saleChance.getAssignMan())){
                saleChance.setAssignTime(new Date());
                saleChance.setState(StateStatus.STATED.getType());
                saleChance.setDevResult(DevResult.DEVING.getStatus());
            }
        }else {
            if (StringUtils.isBlank(saleChance.getAssignMan())){
                saleChance.setAssignTime(null);
                saleChance.setState(StateStatus.UNSTATE.getType());
                saleChance.setDevResult(DevResult.UNDEV.getStatus());
            }else {
                if (!saleChance.getAssignMan().equals(temp.getAssignMan())){
                    saleChance.setAssignTime(new Date());
                }else {
                    saleChance.setAssignTime(temp.getAssignTime());
                }
            }
        }
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) !=1,"更新营销机会失败!!!");
    }


    private void checkSaleChance(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称不能为空!!!!");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空!!!!");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系号码不能为空!!!!");
        AssertUtil.isTrue(! PhoneUtil.isMobile(linkPhone),"联系号码不正确!!!!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChance(Integer[] ids){
        AssertUtil.isTrue(null == ids || ids.length <1,"待删除的记录不存在");
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids) != ids.length,"营销机会数据删除失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChanceDevResult(Integer id, Integer devResult) {
        AssertUtil.isTrue(null == id,"待更新记录不存在");
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null == saleChance,"待更新记录不存在");
        saleChance.setDevResult(devResult);
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) != 1 , "开发状态更新失败");
    }
}
