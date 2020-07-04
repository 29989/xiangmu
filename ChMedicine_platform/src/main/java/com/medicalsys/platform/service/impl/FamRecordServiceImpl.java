package com.medicalsys.platform.service.impl;

import com.google.gson.Gson;
import com.medicalsys.platform.bean.FamRecord;
import com.medicalsys.platform.bean.MeRecord;
import com.medicalsys.platform.dao.FamRecordDao;
import com.medicalsys.platform.service.FamRecordService;
import com.medicalsys.platform.util.MdFileCompare;
import com.medicalsys.platform.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/*
医案服务层
 */
@Service
public class FamRecordServiceImpl implements FamRecordService {
    @Autowired
    private FamRecordDao famRecordDao;
    @Autowired
    private PageUtil pageUtil;
    @Autowired
    private MdFileCompare mdFileCompare;
    @Autowired
    private Gson gson;

    //检索医案
    @Override
    public String findAllFamRecord(String page, String limit, String zdiagnosis, String dialetype) {
        int count=0;
        List<FamRecord> famRecordList=new ArrayList<>();
        //调用脚标算法
        Integer[] pageindex=pageUtil.pageindex(page, limit);
        //检索方式判断
        if((zdiagnosis==null || StringUtils.isEmpty(zdiagnosis)) && (dialetype==null || StringUtils.isEmpty(dialetype))){
            count=famRecordDao.famRecordCount();
            famRecordList=famRecordDao.findAllFamRecord(pageindex[0],pageindex[1]);
        }else{
            if(zdiagnosis!=null && !StringUtils.isEmpty(zdiagnosis)) zdiagnosis="%"+zdiagnosis+"%";
            else {zdiagnosis=new String(); zdiagnosis="%";}
            if(dialetype!=null && !StringUtils.isEmpty(dialetype)) dialetype="%"+dialetype+"%";
            else {dialetype=new String();dialetype="%";}
            count=famRecordDao.famRecordSearchCount(zdiagnosis,dialetype);
            famRecordList=famRecordDao.findAllSearch(pageindex[0],pageindex[1],zdiagnosis,dialetype);
        }
        String famRecordjson = gson.toJson(famRecordList);
        return "{\n" +
                "  \"code\": 0,\n" +
                "  \"count\": " + String.valueOf(count) + ",\n" +
                "  \"data\": " +famRecordjson + "}";
    }

    @Override
    public FamRecord findFamRecordById(String id) {
        return famRecordDao.findFamRecordById(Integer.valueOf(id));
    }

    //添加医案
    @Override
    public String addFamRecord(FamRecord famRecord) {
        int addflag=famRecordDao.addFamRecord(famRecord);
        if(addflag==1){
           return "{\n" +
                "  \"code\": 200,\n" +
                "  \"msg\": \"添加医案成功\",\n" +
                "  \"data\": {\n" +
                "  }\n" +
                "}";
        }else {
            return "{\n" +
                    "  \"code\": 1,\n" +
                    "  \"msg\": \"添加医案失败\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }
    }

    //更新医案
    @Override
    public String updateFamRecord(FamRecord famRecord) {
        int addflag=famRecordDao.updateFamRecord(famRecord);
        if(addflag==1){
            return "{\n" +
                    "  \"code\": 200,\n" +
                    "  \"msg\": \"更新医案成功\",\n" +
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

    //删除医案
    @Override
    public String deleteFamRecord(String[] idsStr) {
        int count=0;
        String notdelet="";
        int length=0;
        for (String s : idsStr) {
            if(StringUtils.isEmpty(s)==false&&s!=null){
                int deleteflag=famRecordDao.deleteFamRecord(Integer.valueOf(s));
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

    //处方比对
    @Override
    public FamRecord findCompare(String zdiagnosis, String dialetype, String drugs, String page, Model model) {
        int count=0;
        List<FamRecord> famRecordList=new ArrayList<>();
        //调用脚标算法
        Integer[] pageindex=pageUtil.pageindex(page, "1");
        //检索方式判断
        if((zdiagnosis==null || StringUtils.isEmpty(zdiagnosis)) && (dialetype==null || StringUtils.isEmpty(dialetype))){
            count=famRecordDao.famRecordCount();
            famRecordList=famRecordDao.findAllFamRecord(pageindex[0],pageindex[1]);
        }else{
            if(zdiagnosis!=null && !StringUtils.isEmpty(zdiagnosis)) zdiagnosis="%"+zdiagnosis+"%";
            else {zdiagnosis=new String(); zdiagnosis="%";}
            if(dialetype!=null && !StringUtils.isEmpty(dialetype)) dialetype="%"+dialetype+"%";
            else {dialetype=new String();dialetype="%";}
            count=famRecordDao.famRecordSearchCount(zdiagnosis,dialetype);
            famRecordList=famRecordDao.findAllSearch(pageindex[0],pageindex[1],zdiagnosis,dialetype);
        }
        //处方比对
        if(famRecordList.size()==0){
            model.addAttribute("countnum", "0");
            model.addAttribute("pagenum", "0");
            model.addAttribute("drugs", drugs);
           return null;
        }else {
            String[] strings=mdFileCompare.compare(famRecordList.get(0), drugs);
            famRecordList.get(0).setDrugs(strings[0]);
            model.addAttribute("countnum", count);
            model.addAttribute("pagenum", pageindex[0]+1);
            model.addAttribute("drugs", strings[1]);
            return famRecordList.get(0);
        }
    }
}
