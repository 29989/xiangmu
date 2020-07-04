package com.medicalsys.platform.controller;
import com.medicalsys.platform.service.impl.DataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
负责处理数据图表请求
 */
@Controller
public class DataController {
    @Autowired
    private DataServiceImpl dataService;


    @GetMapping("/data/usertonday")
    @ResponseBody
    public String userTondayChart(){
      return dataService.userTondayChart();
    }

    @GetMapping("/data/usersource")
    @ResponseBody
    public String userSourceChart(){
        return dataService.userSourceChart();
    }

    @GetMapping("/data/userweeksource")
    @ResponseBody
    public String userWeekSourceChart(){
        return dataService.userWeekSourceChart();
    }
}
