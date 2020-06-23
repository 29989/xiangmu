package com.java.controller;

import com.github.pagehelper.PageInfo;
import com.java.entity.*;
import com.java.service.UserService;
import com.java.utils.CpachaUtil;
import com.java.utils.JSONUtils;
import com.java.utils.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserContrlloer {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/checkCode",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public void checkCode(HttpServletResponse response, HttpServletRequest request){
        CpachaUtil cpachaUtil = new CpachaUtil(4, 120, 40);
        String vCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute("vCode",vCode);
        BufferedImage bufferedImage = cpachaUtil.generatorRotateVCodeImage(vCode, true);
        try {
            ImageIO.write(bufferedImage,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/zhangHao",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String zhangHao(@RequestParam(value = "username") String username){
        User user = userService.getUserName(username);
        ResultInfo resultInfo = new ResultInfo();
        if (user==null){
            resultInfo.setFlag(true);
            resultInfo.setData(user);
        }else {
            resultInfo.setFlag(false);
        }

        return JSONUtils.getJson(resultInfo);
    }

    @RequestMapping(value = "register11",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String zhangHao(User user,@RequestParam(value = "check") String check, HttpServletRequest request){
        ResultInfo resultInfo = new ResultInfo();
        String vCode = (String) request.getSession().getAttribute("vCode");
        if (StringUtils.equalsIgnoreCase(vCode,check)){
            boolean flag =  userService.addUser(user);
            resultInfo.setFlag(flag);

        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码不正确");
        }



        return JSONUtils.getJson(resultInfo);
    }


    @RequestMapping(value = "/loginNamePass",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String loginNamePass(@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "check") String check,
                                HttpServletRequest request){

        ResultInfo resultInfo = new ResultInfo();
        String vCode = (String) request.getSession().getAttribute("vCode");
        if (StringUtils.equalsIgnoreCase(check,vCode)){
            User user = userService.getUserNamePass(username, password);
            if (user==null){
                resultInfo.setFlag(false);
            }else {
                resultInfo.setFlag(true);
                request.getSession().setAttribute("User",user);
            }
        }else {
            resultInfo.setFlag(false);
        }


        return JSONUtils.getJson(resultInfo);
    }


    @RequestMapping(value = "/index11",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String index(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        Object user = request.getSession().getAttribute("User");
        resultInfo.setData(user);
        return JSONUtils.getJson(resultInfo);

    }


    @RequestMapping(value = "/navitem11",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String navitem() {
        ResultInfo resultInfo = new ResultInfo();
        List<Category> category = userService.getCategory();
        if (category!=null){
            resultInfo.setFlag(true);
            resultInfo.setData(category);
        }else {
            resultInfo.setFlag(false);
        }
        return JSONUtils.getJson(resultInfo);

    }

    @RequestMapping(value = "/page_list11",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> page_list11(@RequestParam(value = "cid") Integer cid,

                                        @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ){
        HashMap<String, Object> map = new HashMap<>();
        map.put("cid",cid);
        //pageinfo
        PageInfo<Route> pageInfo = userService.getPageData(cid,pageNum,pageSize);
        map.put("page",pageInfo);


        return map;
    }








    @RequestMapping(value = "/detail11",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String detail(@RequestParam(value = "rid") Integer rid) {
        ResultInfo resultInfo = new ResultInfo();
        Route route = userService.getPageDataImg(rid);
        if (route!=null){
            resultInfo.setFlag(true);
            resultInfo.setData(route);
        }else {
            resultInfo.setFlag(false);
        }
        return JSONUtils.getJson(resultInfo);

    }


    @RequestMapping(value = "/out1",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String out(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();

        request.getSession().setAttribute("User",null);
        Object user = request.getSession().getAttribute("User");
        resultInfo.setData(user);
        return JSONUtils.getJson(resultInfo);

    }

    @RequestMapping(value = "/index111",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String index1(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        Object user = request.getSession().getAttribute("User");
        resultInfo.setData(user);
        return JSONUtils.getJson(resultInfo);

    }

    //收藏方法
    @RequestMapping(value = "/shouCang",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String shouCang(HttpServletRequest request,@RequestParam(value = "rid") Integer rid,
                           @RequestParam(value = "uid") Integer uid) {

        ResultInfo resultInfo = new ResultInfo();

        Date date = new Date(new java.util.Date().getTime());


        Favorite favorite = new Favorite(rid,date,uid);

        boolean flag = userService.getRidUid(favorite);
        resultInfo.setFlag(flag);
        return JSONUtils.getJson(resultInfo);

    }
    //收藏方法
    @RequestMapping(value = "/QuXiaoShouCang",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String QuXiaoShouCang(HttpServletRequest request,@RequestParam(value = "rid") Integer rid,
                           @RequestParam(value = "uid") Integer uid) {

        ResultInfo resultInfo = new ResultInfo();

        boolean flag = userService.getRidUid2(rid,uid);
        resultInfo.setFlag(flag);
        return JSONUtils.getJson(resultInfo);

    }

    @RequestMapping(value = "/favorite",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String favorite(@RequestParam(value = "uid") Integer uid) {
        ResultInfo resultInfo = new ResultInfo();
        List<Route> Route = userService.getFavorite(uid);
        if(Route==null){
            resultInfo.setFlag(false);
        }else {
            resultInfo.setFlag(true);
            resultInfo.setData(Route);
        }

        return JSONUtils.getJson(resultInfo);

    }

    @RequestMapping(value = "/display",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String display(@RequestParam(value = "uid") Integer uid,
            @RequestParam(value = "rid") Integer rid) {
        ResultInfo resultInfo = new ResultInfo();
        Favorite favorite = userService.getFavoriteId(rid,uid);
        Integer count = userService.getCount(rid);
        if(favorite==null){
            resultInfo.setFlag(false);
        }else {
            resultInfo.setFlag(true);

        }
        resultInfo.setData(count);

        return JSONUtils.getJson(resultInfo);

    }

    @RequestMapping(value = "/sc",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String sc(
                          @RequestParam(value = "rid") Integer rid) {
        ResultInfo resultInfo = new ResultInfo();

        Integer count = userService.getCount(rid);


        resultInfo.setData(count);

        return JSONUtils.getJson(resultInfo);

    }

    @RequestMapping(value = "/updataCount",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String update(
            @RequestParam(value = "rid") Integer rid) {
        ResultInfo resultInfo = new ResultInfo();

        Integer count = userService.updataCount(rid);


        resultInfo.setData(count);

        return JSONUtils.getJson(resultInfo);

    }
    //取消收藏
    @RequestMapping(value = "/qxupdataCount",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String qxupdate(
            @RequestParam(value = "rid") Integer rid) {
        ResultInfo resultInfo = new ResultInfo();

        Integer count = userService.updataCountqx(rid);


        resultInfo.setData(count);

        return JSONUtils.getJson(resultInfo);

    }



    @RequestMapping(value = "/paiHang",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> paiHang(
                                          @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize
    ){
        HashMap<String, Object> map = new HashMap<>();
        //pageinfo
        PageInfo<Route> pageInfo = userService.updataCountPaiHang(pageNum,pageSize);
        map.put("page",pageInfo);


        return map;
    }

    @RequestMapping(value = "/souSuo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String souSuo(
            @RequestParam(value = "name") String name,
            HttpServletRequest request) {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(true);
            request.getSession().setAttribute("Name",name);


            return JSONUtils.getJson(resultInfo);

    }

    @RequestMapping(value = "/ss",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String ss(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        Object user = request.getSession().getAttribute("Name");
        resultInfo.setFlag(true);
        resultInfo.setData(user);
        return JSONUtils.getJson(resultInfo);




    }

    @RequestMapping(value = "/page_list2",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> page_list2(@RequestParam(value = "rname") String rname,

                                         @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ){
        HashMap<String, Object> map = new HashMap<>();
        map.put("rname",rname);
        PageInfo<Route> pageInfo = userService.getPageData1(rname,pageNum,pageSize);
        map.put("page",pageInfo);


        return map;
    }






}
