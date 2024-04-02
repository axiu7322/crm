package com.xxx.dao;

import com.xxx.base.BaseMapper;
import com.xxx.model.TreeModel;
import com.xxx.vo.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

        List<TreeModel> queryAllModules();

        List<Module>    queryModuleList();

    Module queryModuleByGradeModuleName(@Param("garde") Integer garde,@Param("moduleName") String moduleName);

    Module queryModuleByGradeAndUrl(@Param("garde")Integer garde,@Param("url") String url);

    Module queryModuleByOptValue(String optValue);

    Integer queryModuleByParentId(Integer id);
}