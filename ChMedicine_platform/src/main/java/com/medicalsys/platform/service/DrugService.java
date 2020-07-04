package com.medicalsys.platform.service;

import com.medicalsys.platform.bean.Drug;

import java.util.List;

/*
药品相关的服务层接口
 */
public interface DrugService {

    //查询检索全部药品
    public String findAllDrug(String page,String limit,String drugname,String efficacy,String categorize);

    //添加药品
    public String addDrug(Drug drug);

    //更新药品
    public String updateDrug(String id,String key,String value);

    //删除药品
    public String deleteDrug(String[] idsStr);

    //查询药品价格信息
    public String findAllDrugPrice();

}
