package com.medicalsys.platform.service.impl;
import com.google.gson.Gson;
import com.medicalsys.platform.bean.DataSourceModel;
import com.medicalsys.platform.bean.DataWeekModel;
import com.medicalsys.platform.bean.MeRecord;
import com.medicalsys.platform.dao.MeRecordDao;
import com.medicalsys.platform.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceImpl {
    @Autowired
    private MeRecordDao meRecordDao;
    @Autowired
    private TimeUtil timeUtil;
    @Autowired
    private Gson gson;

   //周接诊情况
    public String userTondayChart(){
        List<String> datas=timeUtil.getWeekTime();
        List<Integer> nums=new ArrayList<Integer>();
        for (String data : datas) {
            data="%"+data+"%";
            List<MeRecord> meRecordByTime = meRecordDao.findMeRecordByTime(data);
            if(meRecordByTime!=null){
                nums.add(meRecordByTime.size());
            }else {
                nums.add(0);
            }
        }
        String numJson = gson.toJson(nums);
        return "{\n" +
                "  \"code\": 200,\n" +
                "  \"nums\": "+numJson+
                "}";
    }

    //病症百分比
    public String userSourceChart(){
        List<DataSourceModel> dataSourceModels=meRecordDao.findMeRecordByType();
        List<DataSourceModel> sourceModels=null;
        if(dataSourceModels!=null){
            sourceModels=new ArrayList<>();
            List<String> dialetypes=new ArrayList<>();
            for (DataSourceModel dataSourceModel : dataSourceModels) {
                if(!StringUtils.isEmpty(dataSourceModel.getName()) && dataSourceModel!=null){
                dialetypes.add(dataSourceModel.getName());
                sourceModels.add(dataSourceModel);
                }
            }
            String numJson=gson.toJson(sourceModels);
            return "{\n" +
                    "  \"code\": 200,\n" +
                    "  \"nums\": "+numJson+
                    "}";
        }else {
            return "{\n" +
                    "  \"code\": 200,\n" +
                    "}";
        }
    }

    //疾病每天占比
    public String userWeekSourceChart(){
        List<String> namelist=meRecordDao.findAllDialeType();
        List<String> names=new ArrayList<>();
        for (String name : namelist) {
            if(name!=null&&!StringUtils.isEmpty(name)){
                names.add(name);
            }
        }

        if(names!=null&&names.size()!=0){
            List<DataWeekModel> dataWeekModels=new ArrayList<>();
            for (String name : names) {
                DataWeekModel dataWeekModel = new DataWeekModel();
                dataWeekModel.setName(name);
                dataWeekModels.add(dataWeekModel);
            }

            //获得日期
            List<String> datas=timeUtil.getWeekTime();
            for (String data : datas) {
                data="%"+data+"%";
                List<DataSourceModel> dataSourceModels=meRecordDao.findMeRecordByTT(data);
                for (DataWeekModel dataWeekModel : dataWeekModels) {
                    int flag=0;
                    for (DataSourceModel dataSourceModel : dataSourceModels) {
                        if(dataSourceModel.getName().equals(dataWeekModel.getName())) {
                            dataWeekModel.getValue().add(Integer.valueOf(dataSourceModel.getValue()));
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0){
                        dataWeekModel.getValue().add(0);
                    }
                    flag=0;
                }

            }
            String nameJson = gson.toJson(names);
            String numJson= gson.toJson(dataWeekModels);
            return "{\n" +
                    "  \"code\": 200,\n" +
                    " \"names\": "+nameJson+
                    ",  \"nums\": "+numJson+
                    "}";
        }else {
            return "{\n" +
                    "  \"code\": 200,\n" +
                    "}";
        }
    }
}
