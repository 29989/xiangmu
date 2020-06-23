package com.qhit.itravel.service.impl;

import com.qhit.itravel.dao.FavoriteDao;
import com.qhit.itravel.entity.Favorite;
import com.qhit.itravel.entity.Route;
import com.qhit.itravel.dao.RouteDao;
import com.qhit.itravel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Route)表服务实现类
 *
 * @author makejava
 * @since 2020-04-16 15:24:51
 */
@Service("routeService")
public class RouteServiceImpl implements RouteService{
    @Resource
    private RouteDao routeDao;

    @Override
    @Transactional
    public void saveFavorite(Long rid, Integer uid) {
        routeDao.updateFavorite(rid);
        Favorite favorite = new Favorite();
        favorite.setRid(rid.intValue());
        favorite.setUid(uid);
        favorite.setDate(new Date());
        routeDao.insert(favorite);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param rid 主键
     * @return 实例对象
     */

}