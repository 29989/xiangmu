package com.medicalsys.platform.service.impl;

import com.google.gson.Gson;
import com.medicalsys.platform.bean.Drug;
import com.medicalsys.platform.dao.DrugDao;
import com.medicalsys.platform.service.DrugService;
import com.medicalsys.platform.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
/*
药品服务层
 */
@Service
public class DrugServiceImpl implements DrugService {
    @Autowired
    private DrugDao drugDao;
    @Autowired
    private Gson gson;
    @Autowired
    private PageUtil pageUtil;

    //药品列表
    @Override
    public String findAllDrug(String page, String limit, String drugname,String efficacy,String categorize) {
        int count=0;
        List<Drug> drugList=new ArrayList<>();
        //调用脚标算法
        Integer[] pageindex=pageUtil.pageindex(page, limit);
        //检索方式判断
        if((StringUtils.isEmpty(drugname) || drugname==null) && (StringUtils.isEmpty(efficacy)|| efficacy==null)
        &&(StringUtils.isEmpty(categorize)|| categorize==null)){
            count=drugDao.drugCount();
            drugList=drugDao.findAllDrug(pageindex[0],pageindex[1]);
        }else {
            if(drugname!=null && !StringUtils.isEmpty(drugname)) drugname="%"+drugname+"%";
            else {drugname=new String(); drugname="%";}
            if(efficacy!=null && !StringUtils.isEmpty(efficacy)) efficacy="%"+efficacy+"%";
            else {efficacy=new String();efficacy="%";}
            if(categorize!=null && !StringUtils.isEmpty(categorize)) categorize="%"+categorize+"%";
            else {categorize=new String();categorize="%";}
            count=drugDao.drugNameCount(drugname, efficacy, categorize);
            drugList=drugDao.findAllDrugName(pageindex[0],pageindex[1],drugname,efficacy,categorize);
        }
        String drugjson = gson.toJson(drugList);
        return "{\n" +
                "  \"code\": 0,\n" +
                "  \"count\": " + String.valueOf(count) + ",\n" +
                "  \"data\": " + drugjson + "}";
    }


   //添加药品
    @Override
    public String addDrug(Drug drug) {
        int addflag=drugDao.addDrug(drug);
        if(addflag!=1){
            return  "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"msg\": \"添加失败(单位(可能)异常)\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }
        return  "{\n" +
                "  \"code\": 0,\n" +
                "  \"msg\": \"添加成功\",\n" +
                "  \"data\": {\n" +
                "  }\n" +
                "}";
    }

    //更新药品
    @Override
    public String updateDrug(String id, String key, String value) {
        int updateflag=drugDao.updateDrug(Integer.valueOf(id),key, value);
        if(updateflag==1){
            return  "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"msg\": \"更新成功\",\n" +
                    "  \"data\": {\n" +
                    "  }\n" +
                    "}";
        }
        return  "{\n" +
                "  \"code\": 1,\n" +
                "  \"msg\": \"更新失败\",\n" +
                "  \"data\": {\n" +
                "  }\n" +
                "}";
    }

    //删除药品
    @Override
    public String deleteDrug(String[] idsStr) {
        int count=0;
        String notdelet="";
        int length=0;
        for (String s : idsStr) {
           if(StringUtils.isEmpty(s)==false&&s!=null){
            int deleteflag=drugDao.deleteDrug(Integer.valueOf(s));
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

    @Override
    public String findAllDrugPrice() {
        List<Drug> drugList=drugDao.findAllDrugPrice();
        if(drugList!=null&&drugList.size()!=0){
        String drugjson = gson.toJson(drugList);
        return "{\n" +
                "  \"code\": 200,\n" +
                "  \"msg\": \"开启药品价格计算\",\n" +
                "  \"priceinfo\": "+drugjson+
                "}";
        }
        return "{\n" +
                "  \"code\": 1,\n" +
                "  \"msg\": \"药品信息载入出错\",\n" +
                "}";
    }
}
