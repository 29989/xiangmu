package com.qhit.itravel.controller;

import com.qhit.itravel.entity.Favorite;
import com.qhit.itravel.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Favorite)表控制层
 *
 * @author makejava
 * @since 2020-04-16 15:30:00
 */
@RestController
@RequestMapping("favorite")
public class FavoriteController {
    /**
     * 服务对象
     */
    @Resource
    private FavoriteService favoriteService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Favorite selectOne(Integer id) {
        return this.favoriteService.queryById(id);
    }

}