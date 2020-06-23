package com.java.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.entity.Book;
import com.java.entity.BookExample;
import com.java.entity.BookType;
import com.java.mapper.BookMapper;
import com.java.mapper.BookTypeMapper;
import com.java.service.BookService;
import com.java.vo.BookVo;
import com.java.vo.ConditionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookTypeMapper bookTypeMapper;

    @Override
    public List<Book> getBookList() {
        return bookMapper.selectByExample(null);
    }

    @Override
    public boolean addBook(Book book) {
        return bookMapper.insertSelective(book)>0;
    }

    @Override
    public boolean deleteBook(Integer bookid) {
        return bookMapper.deleteByPrimaryKey(bookid)>0;
    }

    @Override
    public Book seleteBookById(Integer id) {
        return bookMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateBook(Book book) {
        return bookMapper.updateByPrimaryKeySelective(book)>0;
    }

    @Override
    public BookVo getBookVoById(Integer id) {
        BookVo bookVo = new BookVo();
        //1)根据id查出图书的基本信息
        Book book = bookMapper.selectByPrimaryKey(id);
        //2)将1中图书信息的跟VO字段相同的copy到BookVO
        BeanUtils.copyProperties(book,bookVo);
        //3)根据1中的book的typeid信息，查询该分类的所有信息
        BookType bookType = bookTypeMapper.selectByPrimaryKey(book.getTypeid());
        //4)将3中typename信息挂载到vo里
        bookVo.setTypename(bookType.getTypename());
        //5)返回VO
        return bookVo;
    }

    @Override
    public PageInfo<BookVo> getPageData(ConditionVo vo, Integer pageNum, Integer pageSize) {

        //查询图书的基本信息
        PageHelper.startPage(pageNum,pageSize);
        BookExample example = new BookExample();
        BookExample.Criteria criteria = example.createCriteria();
        if (vo!=null){
            Integer typeid = vo.getTypeid();
            if (typeid!=null && typeid!=-1){
                criteria.andTypeidEqualTo(typeid);
            }
            String author = vo.getAuthor();
            if(StringUtils.isNotBlank(author)){
                criteria.andAuthorLike("%"+author+"%");
            }

            Double min_price = vo.getMin_price();
            Double max_price = vo.getMax_price();

            if (max_price!=null && min_price!=null){
                criteria.andPriceBetween(min_price,max_price);
            }
        }
        List<Book> books = bookMapper.selectByExample(example);
        PageInfo<Book> info = new PageInfo<>(books);

        //封装BookVO的分页信息
        ArrayList<BookVo> bookVos = new ArrayList<>();
        for (Book book : books) {
            BookVo bookVo = new BookVo();
            //2)将1中图书信息的跟VO字段相同的copy到BookVO
            BeanUtils.copyProperties(book,bookVo);
            //3)根据1中的book的typeid信息，查询该分类的所有信息
            BookType bookType = bookTypeMapper.selectByPrimaryKey(book.getTypeid());
            //4)将3中typename信息挂载到vo里
            bookVo.setTypename(bookType.getTypename());
            //5)添加bookVos
            bookVos.add(bookVo);
        }

        PageInfo<BookVo> pageInfo = new PageInfo<>(bookVos);
        pageInfo.setPageNum(info.getPageNum());
        pageInfo.setPages(info.getPages());
        return pageInfo;
    }
}
