package com.medicalsys.platform.controller;

import com.medicalsys.platform.bean.Drug;
import com.medicalsys.platform.service.impl.DrugServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
中药处理模块
 */
@Controller
public class DrugController {
    @Autowired
    private DrugServiceImpl drugService;

    @GetMapping("/druglist")
    @ResponseBody
    public String druglist(String page,String limit,String drugname,String efficacy,String categorize){
       return drugService.findAllDrug(page, limit, drugname,efficacy,categorize);
   }

   @GetMapping("/drugadd")
   @ResponseBody
    public String drugadd(Drug drug){
       return drugService.addDrug(drug);
   }

   @GetMapping("/drugupdate")
   @ResponseBody
   public String drugUpdate(String id,String key,String value){
        return drugService.updateDrug(id,key, value);
   }

   @GetMapping("/drugdelete")
   @ResponseBody
   public String drugDelete(String[] idsStr){
        return drugService.deleteDrug(idsStr);
   }

   @GetMapping("/drugprice")
   @ResponseBody
   public String drugPrice(){
        return drugService.findAllDrugPrice();
   }

}
