package com.java.controller;

import com.github.pagehelper.PageInfo;
import com.java.entity.Book;
import com.java.entity.BookType;
import com.java.service.BookService;
import com.java.service.BookTypeService;
import com.java.utils.CpachaUtil;
import com.java.utils.JSONUtils;
import com.java.utils.ResultInfo;
import com.java.vo.BookVo;
import com.java.vo.ConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BookContrlloer {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookTypeService bookTypeService;



    @RequestMapping(value = "/getAll",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getAllBooks(){
        List<Book> list = bookService.getBookList();
        ResultInfo info = new ResultInfo();
        if (list!=null && list.size()>0){
            info.setFlag(true);
            info.setData(list);
        }else {
            info.setFlag(false);
        }

        //将info对象转换成json字符串返回
        return JSONUtils.getJson(info);
    }


    @RequestMapping(value = "/page_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> page_list(ConditionVo vo,
                                        @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize
                                        ){
        HashMap<String, Object> map = new HashMap<>();
        //pageinfo
        PageInfo<BookVo> pageInfo = bookService.getPageData(vo,pageNum,pageSize);
        map.put("page",pageInfo);
        //typelist
        List<BookType> types = bookTypeService.getBookTypeList();
        map.put("types",types);
        map.put("vo",vo);
        return map;
    }

    /**
     * 获取所有图书分类信息
     * @return
     */
    @RequestMapping(value = "/getTypeids",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getTypeids(){
        //调，存，转
        List<BookType> types = bookTypeService.getBookTypeList();

        ResultInfo info = new ResultInfo();
        if (types!=null && types.size()>0){
            info.setFlag(true);
            info.setData(types);
        }else {
            info.setFlag(false);
        }

        //将info对象转换成json字符串返回
        return JSONUtils.getJson(info);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String add(Book book){
       boolean flag =  bookService.addBook(book);
       ResultInfo info = new ResultInfo();
       info.setFlag(flag);
        //将info对象转换成json字符串返回
        return JSONUtils.getJson(info);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String delete(@RequestParam(value = "bookid") Integer bookid){
        boolean flag =  bookService.deleteBook(bookid);
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        //将info对象转换成json字符串返回
        return JSONUtils.getJson(info);
    }


    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String detail(@RequestParam(value = "bookid") Integer id){

        //获取BookVo，
        BookVo bookVo = bookService.getBookVoById(id);
        //返回
        ResultInfo info = new ResultInfo();
        if (bookVo!=null){
            info.setFlag(true);
            info.setData(bookVo);
        }else {
            info.setFlag(false);
        }

        //将info对象转换成json字符串返回
        return JSONUtils.getJson(info);
    }

    @RequestMapping(value = "/updateUI",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateUI(@RequestParam(value = "bookid")Integer id){
        //查出要修改的图书
        Book book = bookService.seleteBookById(id);
        //查出所有的图书分类
        List<BookType> types = bookTypeService.getBookTypeList();
        //分别存储到map
        HashMap<String, Object> map = new HashMap<>();
        map.put("book",book);
        map.put("types",types);
        //返回map
        return map;
    }



    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String update(Book book){
        boolean flag =  bookService.updateBook(book);
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        //将info对象转换成json字符串返回
        return JSONUtils.getJson(info);
    }
}
