package com.xxx.service;

import com.xxx.base.BaseService;
import com.xxx.dao.ModuleMapper;
import com.xxx.dao.PermissionMapper;
import com.xxx.model.TreeModel;
import com.xxx.utils.AssertUtil;
import com.xxx.vo.Module;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService extends BaseService<Module,Integer> {
    @Resource
    private ModuleMapper moduleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    public List<TreeModel> queryAllModules(Integer roleId){
        List<TreeModel> treeModelList = moduleMapper.queryAllModules();
        List<Integer> permissionsIds = permissionMapper.queryRoleHasModuleIdsByRoleId(roleId);
        if (permissionsIds != null && permissionsIds.size() > 0){
            treeModelList.forEach(treeModel -> {
                if (permissionsIds.contains(treeModel.getId())){
                    treeModel.setChecked(true);
                }
            });
        }
        return treeModelList;
    }

    public Map<String,Object> queryModuleList(){
        Map<String,Object> map = new HashMap<>();
        List<Module> moduleList = moduleMapper.queryModuleList();
        map.put("code",0);
        map.put("msg","");
        map.put("count",moduleList.size());
        map.put("data",moduleList);
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addModule(Module module) {
        Integer garde = module.getGrade();

        AssertUtil.isTrue(null == garde || !(garde ==0 || garde == 1 || garde ==2),"菜单层级不合法!");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"模块名称不能为空!");
        AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeModuleName(garde,module.getModuleName()),"该层级下菜单名已存在!");
        if (garde == 1) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"URL不能为空");
            AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndUrl(garde,module.getUrl()),"URL不可重复!");
        }
        if (garde == 0) {
            module.setParentId(-1);
        }
        if (garde != 0) {
            AssertUtil.isTrue(null == module.getParentId(),"父级菜单不能为空!");
            AssertUtil.isTrue(null == moduleMapper.selectByPrimaryKey(module.getParentId()),"请指定父级菜单!");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限不能为空！");
        AssertUtil.isTrue(null != moduleMapper.queryModuleByOptValue(module.getOptValue()),"权限已存在！");

        module.setIsValid((byte) 1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleMapper.insertSelective(module)<1,"添加资源失败!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module) {
        AssertUtil.isTrue(module.getId()==null,"待更新记录不存在");
        Module temp = moduleMapper.selectByPrimaryKey(module.getId());
        AssertUtil.isTrue(temp==null,"待更新记录不存在！");
        Integer garde = module.getGrade();
        AssertUtil.isTrue(null == garde || !(garde ==0 || garde == 1 || garde ==2),"菜单层级不合法!");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"模块名称不能为空!");
        temp =  moduleMapper.queryModuleByGradeModuleName(garde, module.getModuleName());
        if (temp!=null){
            AssertUtil.isTrue(!(temp.getId()).equals(module.getId()),"该层级下菜单名一存在");
        }
        if (garde == 1) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"URL不能为空");
            temp =  moduleMapper.queryModuleByGradeAndUrl(garde, module.getUrl());
             if (temp != null){
                AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"该层级下菜单已存在");
            }
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限不能为空！");
        temp = moduleMapper.queryModuleByOptValue(module.getOptValue());
        if (temp!=null){
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"该层级下菜单已存在");
        }
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module) < 1,"修改失败" );
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModule(Integer id) {
        AssertUtil.isTrue(null == id,"待删记录不存在" );
        Module temp  = moduleMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null == temp,"待删除记录不存在");
        Integer count = moduleMapper.queryModuleByParentId(id);
        AssertUtil.isTrue(count >0 , "存在子记录，不能删除!");
        count = permissionMapper.countPermissionByModuleId(id);
        if (count>0){
            permissionMapper.deletePermissionByModuleId(id);
        }
        temp.setIsValid((byte) 0);
        temp.setUpdateDate(new Date());

        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(temp)< 1 ,"删除资源失败");
    }
}
