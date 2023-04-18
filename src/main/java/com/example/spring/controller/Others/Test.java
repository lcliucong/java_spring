package com.example.spring.controller.Others;

import com.alibaba.fastjson2.JSONObject;
import com.example.spring.constant.ErrorEnum;
import com.example.spring.utils.CurlUtil;
import com.example.spring.utils.Error;
import com.example.spring.utils.ErrorMessage;
import com.example.spring.utils.Result;
import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Component
@Slf4j
@RequestMapping(value = "spring")
public class Test {

    /**
     * CollectionUtils  工具类
     * @return json
     */
    @RequestMapping(value = "isEmp")
    public Result collect(){
        List<String> list = new ArrayList<>(){{
            add(0,"emp");
            add(1,"notemp");
        }};
        List<Integer> itList = new ArrayList<>();
        if (CollectionUtils.isEmpty(itList)){
            return Result.success("empty");
        }
        if (CollectionUtils.isEmpty(list)){
            return Result.success("not Empty");
        }
        return Result.success();
    }
    @RequestMapping(value = "googleList")
    public Result googleList(){
        List<Integer> lists = Lists.newArrayList();
        for (int i =0; i<=200; i++){
            lists.add(i,i);
        }
        //将list分为50一组
        List<List<Integer>> reList = Lists.partition(lists,50);
        return Result.success(reList);
    }
    /**
     * Objects类
     * 对象为空抛出异常  Objects.requireNonNull(变量, ()->抛出的错误信息)
     */
    @RequestMapping(value = "Objects")
    public Result ObjectsE(@RequestBody String jsonParam){
        //获取参数num时，为空则直接返回code => 50004, ()-> 指定消息
        JSONObject jsparam = JSONObject.parseObject(jsonParam);
        Integer it1 = jsparam.getInteger("num");
        Objects.requireNonNull(it1, ()-> "不能为空");
        return Result.success();
    }
    /**
     * StringUtils 工具类 + 断言
     * StringUtils.isBlank(String str)  判断字符串是否为空,  null, "", " " 三种情况
     * StringUtils.split(String str, ","); 分割字符串
     * StringUtils.isNumeric(String str) 判断字符串是否是纯数字
     * StringUtils.join(List list, "你想用什么符号转换为list")  将集合拼接成字符串
     */
    @RequestMapping(value = "StringUtils")
    public Result StringUti(@RequestBody Map<?, ?> params){
        String str1 = String.valueOf(params.get("str"));
        // null?? 暂时不准确
        if (StringUtils.isBlank(str1)){
            return Result.success("参数为空了");
        }
        //分割字符串
        String str2 = "this,is,a,string";
        String[] strArr = StringUtils.split(str2,",");
        for (String str : strArr){
            System.out.println(str);
        }
        //是否是纯数字
        String str3 = String.valueOf(params.get("str2"));
        if (StringUtils.isNumeric(str3)){
            System.out.println("是纯数字");
        }else{
            System.out.println("不是纯数字");
        }
        List<String> list = Lists.newArrayList("string","a","is","this");
        //list  顺序反转  转换后为 this is a string
        List<String> reverse = Lists.reverse(list);
        //用,将list 转换为 String
        String ListToString = StringUtils.join(reverse,",");
        //用  将list转换为String
        String ListToStringWithNotCharacter = StringUtils.join(reverse," ");
        System.out.println("颠倒后的list: "+reverse);
        System.out.println("用,将list转换为String: "+ListToString);
        System.out.println("用空格将list转换为String: "+ListToStringWithNotCharacter);
        assert params.get("str5") != null : "参数str2不能为空";
        return Result.success(str3);
    }

    /**
     *  reason
     *  main work
     *  target
     *  enum错误抛出   enum异常抛出
     */
    @RequestMapping(value = "Utils", method = RequestMethod.POST)
    public Result Utils(@RequestBody Map<?,?> mapParam) throws ErrorMessage {
        List<Integer> list = Lists.newArrayList(1,2,3,4,5);
        String reason = String.valueOf(mapParam.get("reason"));
        if (StringUtils.isBlank(reason)){
            throw new ErrorMessage(ErrorEnum.COMMON_ERROR.getCode(), ErrorEnum.COMMON_ERROR.getMessage());
        }
        List<String> strList = Lists.newArrayList(
      "element",
                "element2",
                "element3","element4"
        );
        List<String> reverList = Lists.reverse(strList);
        System.out.println(strList);
        return Result.success(strList);
    }



    /**
     * 模拟出票
     * @return
     */
    @RequestMapping(value = "makeTicket", method = RequestMethod.POST)  //横店出票
    public Result makeTicket(){
        HashMap<String, Object> param = new HashMap<>();
        param.put("cinema_code",31183901); //影院id
        param.put("film_name","灌篮高手"); //影片名称
        param.put("seats_ids","04排14座"); //座位id
        param.put("screens_name","1号激光厅"); //大厅
        param.put("show_time","2023-04-20 14:30:00"); //开场时间
        param.put("cateye_price","60"); //开场时间
        param.put("user_pay","57"); //开场时间
        param.put("mission_id","666888999"); //开场时间
        param.put("expire","1681552800157"); //开场时间
        HttpEntity<String> res = CurlUtil.post("http://124.220.14.245:1815/create_order",param);
        return Result.success(res);
    }

    @RequestMapping(value = "tuipiao", method = RequestMethod.POST)  //横店退票
    public Result refundTicket(){
        HashMap<String, Object> param = new HashMap<>();
        param.put("order_no","1681465433880830"); //订单号
        HttpEntity<String> res = CurlUtil.post("http://124.220.14.245:1815/refund_order",param);

        return Result.success();
    }

    @RequestMapping(value = "makeUmeTicket", method = RequestMethod.POST)  //UME出票
    public Result makeUmeTicket(){
        HashMap<String, Object> param = new HashMap<>();
        param.put("cinema_code",50090501); //影院id
        param.put("film_name","宇宙探索编辑部"); //影片名称
        param.put("seats_ids","C排4座"); //座位id
        param.put("screens_name","4号厅"); //大厅
        param.put("show_time","2023-04-16 11:00:00"); //开场时间
        param.put("cateye_price","60"); //开场时间
        param.put("user_pay","57"); //开场时间
        param.put("mission_id","666888999"); //开场时间
        param.put("expire","1681552800157"); //开场时间
        HttpEntity<String> res = CurlUtil.post("http://124.220.14.245:1813/create_order",param);
        return Result.success(res);
    }

    /**
     *
     */
    @RequestMapping(value = "test", method = RequestMethod.POST)
    public Result test(){

        return Result.success();
    }





}
