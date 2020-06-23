package com.qhit.itravel.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhit.itravel.dao.RouteDao;
import com.qhit.itravel.entity.Category;
import com.qhit.itravel.entity.Route;
import com.qhit.itravel.entity.RouteImg;
import com.qhit.itravel.entity.Seller;
import com.qhit.itravel.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
@RequestMapping("/front")
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RouteDao routeDao;


//    @Resource
//    private RedisUtil redisUtil;

    @RequestMapping("/index")
    public String toIndex(Model model){
//        List<Object> categoriesList = redisUtil.rangeList("categoriesList", 0, -1);
//        if (categoriesList == null || categoriesList.size()==0){
//            List<Category> list = categoryService.queryAll();
//            redisUtil.lSet("categoriesList",list);
//            model.addAttribute("categories",list);
//        }else {
//            model.addAttribute("categories",categoriesList.get(0));
//            System.out.println(categoriesList.get(0));
//        }


        List<Category> list = categoryService.queryAll();
        model.addAttribute("categories",list);
        return "index";
    }

    @GetMapping("/getRouteList")
    public String getRouteList(@RequestParam("cid") Integer cid,Model model,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<Route> routes = routeDao.getRouteListByCid(cid);

        PageInfo<Route> pageInfo = new PageInfo<>(routes);

        List<Category> list = categoryService.queryAll();
        model.addAttribute("categories",list);

        model.addAttribute("pageInfo",pageInfo);


        return "route_list";
    }

    @GetMapping("/routedetail")
    public String routeDetail(@RequestParam("rid") Long rid,Model model){

        List<Category> list = categoryService.queryAll();
        model.addAttribute("categories",list);

        Route route = routeDao.getById(rid);
        Seller seller =  routeDao.getAllSeller(route.getSid());

        List<RouteImg> routeImgs = routeDao.getRountImgByRid(route.getRid());
        model.addAttribute("route",route);
        model.addAttribute("seller",seller);
        model.addAttribute("routeImgs",routeImgs);

        return "route_detail";
    }
}
