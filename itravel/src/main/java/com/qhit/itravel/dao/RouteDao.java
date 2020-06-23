package com.qhit.itravel.dao;

import java.util.List;
import java.util.Map;

import com.qhit.itravel.entity.Favorite;
import com.qhit.itravel.entity.RouteImg;
import com.qhit.itravel.entity.Seller;
import org.apache.ibatis.annotations.*;

import com.qhit.itravel.entity.Route;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RouteDao {

    @Select("select * from route t where t.rid = #{rid}")
    Route getById(Long rid);

    @Delete("delete from route where rid = #{rid}")
    int delete(Long rid);

    int update(Route route);
    
    @Options(useGeneratedKeys = true, keyProperty = "rid")
    @Insert("insert into route(rid, rname, price, routeIntroduce, rflag, rdate, isThemeTour, count, cid, rimage, sid, sourceId) values(#{rid}, #{rname}, #{price}, #{routeIntroduce}, #{rflag}, #{rdate}, #{isThemeTour}, #{count}, #{cid}, #{rimage}, #{sid}, #{sourceId})")
    int save(Route route);
    
    int count(@Param("params") Map<String, Object> params);

    List<Route> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("select * from route t where t.cid = #{cid}")
    List<Route> getRouteListByCid(Integer cid);

    @Select("select * from seller where sid = #{sid}")
    Seller getAllSeller(Integer sid);


    @Update("update route set count = count+1 where rid = #{rid}")
    void updateFavorite(Long rid);

    @Select("select * from route")
    List<Route> queryAll();

    @Insert("insert into favorite(rid, date, uid) values(#{rid}, #{date}, #{uid})")
    int insert(Favorite favorite);

    @Select("select * from route_img where rid = #{rid}")
    List<RouteImg> getRountImgByRid(Integer rid);
}
