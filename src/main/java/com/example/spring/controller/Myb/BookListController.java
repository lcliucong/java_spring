package com.example.spring.controller.Myb;

import com.alibaba.fastjson2.JSONObject;
import com.example.spring.filter.BookListFilter;
import com.example.spring.filter.Filters;
import com.example.spring.service.impl.BookServiceImpl;
import com.example.spring.utils.CommonRes;
import com.example.spring.utils.PageUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.spring.pojo.BookModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController //@controller和@ResponseBody合体
@Component  //作为组件类存在
@Slf4j
@RequestMapping(value = "/books")  //book路由
public class BookListController {

    @Resource
    BookServiceImpl bookServiceImpl;
    //增加一条数据
    @RequestMapping(value = "/addbook", method = RequestMethod.POST)
    public CommonRes addbook(@Validated @RequestBody BookModel bookModel) throws Exception {
        bookServiceImpl.saves(bookModel);
        return CommonRes.create();
    }
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public Object getAll(@RequestBody @Validated BookListFilter bookListFilter){
        formatPageHelper(bookListFilter);
        //获取全部数据
        List<BookModel> list = bookServiceImpl.getAll();
        //进行分页处理
        PageInfo<BookModel> pageInfo = new PageInfo<>(list);
        //调用结果整理方法
        PageUtils<BookModel> pageUtils = new PageUtils<>();
        HashMap<String, Object> result = pageUtils.returnMake(pageInfo);
        //统一返回格式
        return CommonRes.create(result);
    }

    /**
     *
     *  更新数据信息
     */
    @RequestMapping(value = "updateBook", method=RequestMethod.POST)
    public CommonRes upd(@RequestBody @Validated BookListFilter bookListFilter){
        BookModel bookModel = new BookModel();
        bookModel.setBookName(bookListFilter.getBookName());
        bookModel.setBookNumber(bookListFilter.getBookNumber());
        bookModel.setBookAmount(bookListFilter.getBookAmount());
        bookModel.setBookStatus(bookListFilter.getBookStatus());
//        bookModel.setBookYear(bookListFilter.getBookYear());
        bookModel.setBookAuth(bookListFilter.getBookAuth());
        bookModel.setId(bookListFilter.getId());
        int res = bookServiceImpl.updateInfo(bookModel);
        return CommonRes.create(res);
    }

    /**
     *  删除某条数据
     */
    @RequestMapping(value = "delbook", method = RequestMethod.POST)
    public CommonRes del(@RequestBody BookListFilter bookListFilter){
        int result = bookServiceImpl.delBook(bookListFilter.getId());
        return CommonRes.create(result);
    }

    /**
     * 软删除
     */
    @RequestMapping(value = "changeStatus", method = RequestMethod.GET)
    public CommonRes softDel(@RequestParam int bookid){
        int result = bookServiceImpl.softDel(bookid);
        return CommonRes.create(bookid);
    }

    /**
     * 根据书名和作者查询
     */
    @RequestMapping(value = "select", method = RequestMethod.POST)
    public CommonRes selectBy(@RequestBody @Validated BookListFilter bookListFilter){
        BookModel bookModel = new BookModel();
        bookModel.setBookName(bookListFilter.getBookName());
        bookModel.setBookAuth(bookListFilter.getBookAuth());
        bookModel.setBookNumber(bookListFilter.getBookNumber());
        bookModel.setBookStatus(bookListFilter.getBookStatus());
        log.info("bookStatus ：{}",bookListFilter.getBookStatus());
        log.info("bookName : {}", bookListFilter.getBookName());
        List<BookModel> list = bookServiceImpl.selectBy(bookModel);
        //增加分页
        PageInfo<BookModel> pageInfo = new PageInfo<>(list);
        HashMap<String,Object> results = new HashMap<>();
        results.put("size",pageInfo.getSize());
        results.put("pageNum", pageInfo.getPageNum());
        results.put("total",pageInfo.getTotal());
        results.put("list",pageInfo.getList());
        return CommonRes.create(results);
    }

    /**
     * 表联查  user_list表book_id  =  book表id
     */
    @RequestMapping(value = "getBookByUser", method = RequestMethod.POST)
    public CommonRes getByUser(@RequestBody @Validated BookListFilter bookListFilter){
//        formatPageHelper(bookListFilter);   //加上orderBy会说字段id模糊不清
        PageHelper.startPage(bookListFilter.getPage(),bookListFilter.getSize());
        BookModel bookModel = new BookModel();
        bookModel.setId(bookListFilter.getId());
        bookModel.setBookName(bookListFilter.getBookName());
        bookModel.setBookAuth(bookListFilter.getBookAuth());
        List<BookModel> list = bookServiceImpl.getByUser(bookModel);
        PageInfo<BookModel> pageInfo = new PageInfo<>(list);
        PageUtils<BookModel> pageUtils = new PageUtils<>();
        HashMap<String, Object> result = pageUtils.returnMake(pageInfo);
        return CommonRes.create(result);
    }

    /**
     * 一些验证注解
     */
    @RequestMapping(value = "inst", method = RequestMethod.POST)
    public CommonRes insertOne(@RequestBody @Validated BookListFilter bookListFilter){
        BookModel bookModel = new BookModel();
        bookModel.setBookName(bookListFilter.getBookName());
//        bookModel.setBookYear(bookListFilter.getBookYear());
        bookModel.setBookNumber(bookListFilter.getBookNumber());
        bookModel.setBookAmount(bookListFilter.getBookAmount());
        bookModel.setBookStatus(bookListFilter.getBookStatus());
        bookModel.setBookAuth(bookListFilter.getBookAuth());
        return CommonRes.create();
    }
    @RequestMapping(value = "getByBookName",method = RequestMethod.POST)
    public CommonRes getByBName(@RequestBody @Validated BookListFilter bookListFilter){
        BookModel bookModel = new BookModel();
        bookModel.setBookName(bookListFilter.getBookName());
        List<BookModel> list = bookServiceImpl.getByname(bookModel);
        return CommonRes.create(list);
    }
    public void formatPageHelper(Filters filters){
        PageHelper.startPage(filters.getPage(), filters.getSize());
        PageHelper.orderBy(filters.getOrderBy());
    }
}
