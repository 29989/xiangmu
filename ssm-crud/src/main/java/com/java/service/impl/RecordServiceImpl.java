package com.java.service.impl;

import com.java.entity.Record;
import com.java.dao.RecordDao;
import com.java.service.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Record)表服务实现类
 *
 * @author makejava
 * @since 2020-04-30 15:21:12
 */
@Service("recordService")
public class RecordServiceImpl implements RecordService {
    @Resource
    private RecordDao recordDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Record queryById(Integer id) {
        return this.recordDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Record> queryAllByLimit(int offset, int limit) {
        return this.recordDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    @Override
    public Record insert(Record record) {
        this.recordDao.insert(record);
        return record;
    }

    /**
     * 修改数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    @Override
    public Record update(Record record) {
        this.recordDao.update(record);
        return this.queryById(record.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.recordDao.deleteById(id) > 0;
    }
}