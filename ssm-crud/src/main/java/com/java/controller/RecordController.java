package com.java.controller;

import com.java.entity.Record;
import com.java.service.RecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Record)表控制层
 *
 * @author makejava
 * @since 2020-04-30 15:21:13
 */
@RestController
@RequestMapping("record")
public class RecordController {
    /**
     * 服务对象
     */
    @Resource
    private RecordService recordService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @RequestMapping("selectOne")
    public Record selectOne(Integer id) {
        return this.recordService.queryById(id);
    }

}