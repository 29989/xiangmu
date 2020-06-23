package com.qhit.itravel.service;

import com.qhit.itravel.entity.Route;
import java.util.List;

/**
 * (Route)表服务接口
 *
 * @author makejava
 * @since 2020-04-16 15:24:51
 */
public interface RouteService {


    void saveFavorite(Long rid, Integer uid);
}