package com.medicalsys.platform.service.impl;
import com.google.gson.Gson;
import com.medicalsys.platform.bean.MeRecord;
import com.medicalsys.platform.bean.User;
import com.medicalsys.platform.dao.MeRecordDao;
import com.medicalsys.platform.service.MeRecordService;
import com.medicalsys.platform.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

/*
诊疗记录服务层
 */
@Service
public class MeRecordServiceImpl implements MeRecordService {
    @Autowired
    private MeRecordDao meRecordDao;
    @Autowired
    private PageUtil pageUtil;
    @Autowired
    private Gson gson;

    //检索记录
    @Override
    public String findAllMeRecord(String page, String limit, String name, String time) {
        int count=0;
        List<MeRecord> meRecordList=new ArrayList<>();
        //调用脚标算法
        Integer[] pageindex=pageUtil.pageindex(page, limit);
        //检索方式判断
        if((name==null || StringUtils.isEmpty(name)) && (time==null || StringUtils.isEmpty(time))){
            count=meRecordDao.meRecordCount();
            meRecordList=meRecordDao.findAllMeRecord(pageindex[0],pageindex[1]);
        }else{
            if(name!=null && !StringUtils.isEmpty(name)) name="%"+name+"%";
            else {name=new String(); name="%";}
            if(time!=null && !StringUtils.isEmpty(time)) time="%"+time.split("\\s+")[0]+"%";
            else {time=new String();time="%";}
            count=meRecordDao.meRecordSearchCount(name, time);
            meRecordList=meRecordDao.findAllSearch(pageindex[0],pageindex[1],name,time);
        }
        String meRecordjson = gson.toJson(meRecordList);
        return "{\n" +
                "  \"code\": 0,\n" +
                "  \"count\": " + String.valueOf(count) + ",\n" +
                "  \"data\": " +meRecordjson + "}";
    }

    //今日诊疗记录
    @Override
    public String findAllMeRecordID(String page, String limit, String name, String time, HttpSession session) {
        List<MeRecord> meRecordList=new ArrayList<>();
        //调用脚标算法
        Integer[] pageindex=pageUtil.pageindex(page, limit);
        //检索方式判断
        if((name==null || StringUtils.isEmpty(name)) && (time==null || StringUtils.isEmpty(time))){
            meRecordList=meRecordDao.findAllMeRecord(pageindex[0],pageindex[1]);
        }else{
            if(name!=null && !StringUtils.isEmpty(name)) name="%"+name+"%";
            else {name=new String(); name="%";}
            if(time!=null && !StringUtils.isEmpty(time)) time="%"+time.split("\\s+")[0]+"%";
            else {time=new String();time="%";}
            meRecordList=meRecordDao.findAllSearch(pageindex[0],pageindex[1],name,time);
        }
        User user = (User) session.getAttribute("user");
        List<MeRecord> meRecordListID=new ArrayList<>();
        //过滤其它医生记录
        for (MeRecord meRecord : meRecordList) {
            if(user.getId()==meRecord.getUserid()){
                meRecordListID.add(meRecord);
            }
        }

        String meRecordjson = gson.toJson(meRecordListID);
        return "{\n" +
                "  \"code\": 0,\n" +
                "  \"count\": " + String.valueOf(meRecordListID.size()) + ",\n" +
                "  \"data\": " +meRecordjson + "}";
    }


    //返回指定记录
    @Override
    public MeRecord findMeRecordById(String id) {
        return meRecordDao.findMeRecordById(Integer.valueOf(id));
    }

    //添加诊疗记录
    @Override
    public String addMeRecord(MeRecord meRecord,HttpSession session) {
        User user= (User) session.getAttribute("user");
        meRecord.setUserid(user.getId());
        int addflag=meRecordDao.addMeRecord(meRecord);
        if(addflag==1){
            return "{\n" +
                    "  \"code\": 200,\n" +
                    "  \"msg\": \"添加记录成功\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }else {
            return "{\n" +
                    "  \"code\": 1,\n" +
                    "  \"msg\": \"添加失败\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }
    }

    //更新诊疗记录
    @Override
    public String updateMeRecord(MeRecord meRecord) {
        int addflag=meRecordDao.updateMeRecord(meRecord);
        if(addflag==1){
            return "{\n" +
                    "  \"code\": 200,\n" +
                    "  \"msg\": \"更新记录成功\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }else {
            return "{\n" +
                    "  \"code\": 1,\n" +
                    "  \"msg\": \"更新失败\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }
    }

    //删除诊疗记录
    @Override
    public String deleteMeRecord(String[] idsStr) {
        int count=0;
        String notdelet="";
        int length=0;
        for (String s : idsStr) {
            if(StringUtils.isEmpty(s)==false&&s!=null){
                int deleteflag=meRecordDao.deleteMeRecord(Integer.valueOf(s));
                count=count+deleteflag;
                length++;
                if(deleteflag==0) notdelet=notdelet+s;
            }
        }
        if(count==length){
            return  "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"msg\": \"删除成功\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }else {
            return  "{\n" +
                    "  \"code\": 1,\n" +
                    "  \"msg\": \"删除"+notdelet+"失败\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }
    }


}
