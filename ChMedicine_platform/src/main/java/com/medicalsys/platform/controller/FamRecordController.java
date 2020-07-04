package com.medicalsys.platform.controller;
import com.medicalsys.platform.bean.FamRecord;
import com.medicalsys.platform.service.impl.FamRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
医案响应层
 */
@Controller
public class FamRecordController {
    @Autowired
    private FamRecordServiceImpl famRecordService;

    //医案列表
    @GetMapping("/fmRecordList")
    @ResponseBody
    public String fmRecordList(String page,String limit,String zdiagnosis,String dialetype){
     return famRecordService.findAllFamRecord(page, limit, zdiagnosis, dialetype);
    }

    //医案详情页
    @GetMapping("/fmrecord/detail")
    public String fmRecordDetail(String id, Model model){
        FamRecord famRecord=famRecordService.findFamRecordById(id);
        model.addAttribute("detail",famRecord);
        return "pages/record/f-edit";
    }

    //医案添加
    @GetMapping("/famrecordadd")
    @ResponseBody
    public String fmRecordAdd(FamRecord famRecord){
        return famRecordService.addFamRecord(famRecord);
    }

    //更新医案
    @GetMapping("/famrecordupdate")
    @ResponseBody
    public String fmRecordUpdate(FamRecord famRecord){
        return famRecordService.updateFamRecord(famRecord);
    }

    //删除医案
    @GetMapping("/famrecorddelete")
    @ResponseBody
    public String drugDelete(String[] idsStr){
        return famRecordService.deleteFamRecord(idsStr);
    }
}
