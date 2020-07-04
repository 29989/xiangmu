package com.medicalsys.platform.service;

import com.medicalsys.platform.bean.FamRecord;
import com.medicalsys.platform.bean.MeRecord;

import javax.servlet.http.HttpSession;

/*
诊疗的服务层接口
 */
public interface MeRecordService {
    //查询检索全部医疗档案
    public String findAllMeRecord(String page,String limit,String name,String time);

    //今日诊疗记录
    public String findAllMeRecordID(String page, String limit, String name, String time, HttpSession session);

    //查找指定医疗档案
    public MeRecord findMeRecordById(String id);


    //添加诊疗记录
    public String addMeRecord(MeRecord meRecord,HttpSession session);

    //更新诊疗记录
    public String updateMeRecord(MeRecord meRecord);

    //删除诊疗记录
    public String deleteMeRecord(String[] idsStr);

}
