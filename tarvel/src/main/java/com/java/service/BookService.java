package com.java.service;

import com.github.pagehelper.PageInfo;
import com.java.entity.Book;
import com.java.vo.BookVo;
import com.java.vo.ConditionVo;

import java.util.List;

public interface BookService {
    List<Book> getBookList();

    boolean addBook(Book book);

    boolean deleteBook(Integer bookid);

    Book seleteBookById(Integer id);

    boolean updateBook(Book book);

    BookVo getBookVoById(Integer id);

    PageInfo<BookVo> getPageData(ConditionVo vo, Integer pageNum, Integer pageSize);
}
