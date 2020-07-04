package com.medicalsys.platform.controller;
import com.medicalsys.platform.bean.FamRecord;
import com.medicalsys.platform.bean.MeRecord;
import com.medicalsys.platform.service.impl.FamRecordServiceImpl;
import com.medicalsys.platform.service.impl.MeRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MdFileController {
    @Autowired
    private MeRecordServiceImpl meRecordService;
    @Autowired
    private FamRecordServiceImpl famRecordService;


    //诊疗列表
    @GetMapping("/mdfilelist")
    @ResponseBody
    public String mdFileList(String page,String limit,String name,String time){
        return meRecordService.findAllMeRecord(page, limit, name, time);
    }

    //今日诊疗
    @GetMapping("/tonpatlist")
    @ResponseBody
    public String patientList(String page,String limit,String name,HttpServletRequest request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //测试传递
        return meRecordService.findAllMeRecordID(page, limit, name,sdf.format(new Date()),request.getSession());
    }

    //诊疗记录查看
    @GetMapping("/mdfile/detail")
    public String mdfile(String id, Model model)
    {
        MeRecord meRecord=meRecordService.findMeRecordById(id);
        model.addAttribute("detail",meRecord);
        return "pages/file/mdfiledetail";

    }

    //今日诊疗记录查看
    @GetMapping("/tonpatient/edit")
    public String tonpatEdit(String id, Model model)
    {
        MeRecord meRecord=meRecordService.findMeRecordById(id);
        model.addAttribute("detail",meRecord);
        return "pages/patient/patient-edit";

    }

    //处方比对
     //首次点击
    @GetMapping("/tonpatient/compare")
    public void tonPanCompare(String zdiagnosis, String dialetype, String drugs,HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession();
        session.setAttribute("zdsis", zdiagnosis);
        session.setAttribute("dialetype", dialetype);
        session.setAttribute("drugs", drugs);
        //开启处方页面的重置
        session.setAttribute("relaxdrus", "1");
        try {
            response.sendRedirect("/patient/compare");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/patient/compare")
    public String compare( String page, Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        String zdiagnosis= (String) session.getAttribute("zdsis");
        String dialetype= (String) session.getAttribute("dialetype");
        String drugs= (String) session.getAttribute("drugs");
        //开启处方页面的重置
        session.setAttribute("relaxdrus", "1");

        FamRecord famRecord=famRecordService.findCompare(zdiagnosis, dialetype, drugs,page,model);
        session.setAttribute("drugscmrsut", model.getAttribute("drugs"));
        model.addAttribute("detail",famRecord);
        return "pages/patient/compare";
    }

    @GetMapping("/drugs/compare")
    @ResponseBody
    public String drugUp(String drugs,HttpServletRequest request){
        HttpSession session=request.getSession();
        session.setAttribute("drugs", drugs);
        String flag= (String) session.getAttribute("relaxdrus");
        String drugscmrsut= (String) session.getAttribute("drugscmrsut");
        if(flag!=null&&flag.equals("1")){
            //开启处方页面的重置
            session.setAttribute("relaxdrus", "0");
            return drugscmrsut+"-*-"+"1";
        }
        return drugscmrsut+"-*-"+"0";
    }



    //处方入库
    @GetMapping("/tonpatiend/add")
    @ResponseBody
    public String tonPanAdd(MeRecord meRecord,HttpServletRequest request){
        return meRecordService.addMeRecord(meRecord,request.getSession());
    }

    //处方修改
    @GetMapping("/tonpatiend/update")
    @ResponseBody
    public String tonPanUpdate(MeRecord meRecord){
        return meRecordService.updateMeRecord(meRecord);
    }

    //处方删除
    @GetMapping("/merecorddelete")
    @ResponseBody
    public String drugDelete(String[] idsStr){
        return meRecordService.deleteMeRecord(idsStr);
    }
}
