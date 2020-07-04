package com.medicalsys.platform.service;

import com.medicalsys.platform.bean.FamRecord;
import com.medicalsys.platform.bean.MeRecord;
import org.springframework.ui.Model;

import java.util.List;

/*
医案服务层接口
 */
public interface FamRecordService {
    //查询检索全部医案
    public String findAllFamRecord(String page,String limit,String zdiagnosis,String dialetype);

    //查找指定医案
    public FamRecord findFamRecordById(String id);

    //添加医案
    public String addFamRecord(FamRecord famRecord);

    //更新医案
    public String updateFamRecord(FamRecord famRecord);

    //删除医案
    public String deleteFamRecord(String[] idsStr);

    //查询检索医案（处方比对）
    public FamRecord findCompare(String zdiagnosis, String dialetype, String drugs, String page, Model model);
}
