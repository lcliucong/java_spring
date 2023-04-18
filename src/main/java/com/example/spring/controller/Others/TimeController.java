package com.example.spring.controller.Others;

import com.example.spring.utils.Result;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "time")
public class TimeController {
    /**
     * 创建13位毫秒级时间戳
     */
    @RequestMapping(value = "create")
    public Result create(@RequestParam String times){
        //创建13位毫米级时间戳
        long timeStamp = System.currentTimeMillis();
        //获取当前时间    年月日时分秒
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        return Result.success(date);
    }

    /**
     * 将时间戳转换为时间 / 将时间转换为时间戳
     */
    @RequestMapping(value = "datetotime")
    public Result datetotime(@RequestParam String dates) throws ParseException {
        //格式要与传递参数一样，比如yyyy-MM-dd传参：2023-03-07, yyyy/MM/dd传参2023/03/07
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(dates);
        long time = date.getTime();
        /*
        * 时间戳转日期
        * */
        long times = new Date().getTime();
        String d = simpleDateFormat.format(new Date(times));
        return Result.success(d);
    }
    /**
     * 获取当前时间戳  13位毫秒
     */
    @RequestMapping(value = "getTime")
    public Result getTime(){
        long times = new Date().getTime();
        long times1 = System.currentTimeMillis();

        return Result.success(times1);
    }
    /**
     * 获取当前日期 年月日-时分秒
     */
    @RequestMapping(value = "getDate")
    public Result getDate() throws ParseException {
        //规定 年月日-时分秒格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date() 为2023-04-15T03:23:53.157+00:00
        String dates = simpleDateFormat.format(new Date());
        return Result.success(dates);
    }
    /**
     * 日期规范格式
     */
    @RequestMapping(value = "toDate")
    public Result toDate(@RequestParam String date) throws ParseException {
        long timeStamp = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dates = sdf.format(timeStamp);
        return Result.success(dates);
    }

    /***
     * 时间处理
     */
    @RequestMapping(value = "timeController")
    public Result makeTimeFormat(@RequestBody Map<?,?> params) throws ParseException {
        String dateStart = String.valueOf(params.get("start_time"));
        String dateEnd = String.valueOf(params.get("end_time"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateStart);

        return Result.success(date);
    }
}
