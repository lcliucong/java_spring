package com.example.spring.controller.Others;

import com.alibaba.fastjson2.JSONObject;
import com.example.spring.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import com.google.common.collect.Lists;
@Slf4j
@RestController
@RequestMapping(value = "coll")
public class CollectionsController {
    /**
     * Java.util.Collections工具包
     */
    @RequestMapping(value = "sorts")
    public Result sorts(){
        List<Integer> mk_list = new ArrayList<>(){{
            add(1);add(3);
            add(4);add(2);
        }};
        log.info("before sort: {}", JSONObject.toJSONString(mk_list));
        //升序sort
        Collections.sort(mk_list);
        log.info("sort: {}", JSONObject.toJSONString(mk_list));
        //降序reverse
        Collections.reverse(mk_list);
        log.info("reverse: {}" , JSONObject.toJSONString(mk_list));
        //max_value
        Integer max = Collections.max(mk_list);
        log.info("max: {}",max);
        Integer min = Collections.min(mk_list);
        log.info("min: {}", min);
        return Result.success(mk_list);
    }

    /**
     * 根据值获取索引位置
     */
    @RequestMapping(value = "binarySearch")
    public Result binarySearch(){
        List<Integer> list = List.of(5,9,13,2);
        int res = Collections.binarySearch(list,13);
        log.info("binarySearch: {}",res);
        return Result.success();
    }

    /**
     * 创建不可修改的数组
     */
    @RequestMapping(value = "unmodify")
    public Result unmodify(){
        List<Object> list = new ArrayList<>(){{
            add("string");add(2);add("password");
        }};
        List<Object> unmodify =Collections.unmodifiableList(list);
        unmodify.add(3);
        log.info("unmodify: {}", JSONObject.toJSONString(unmodify));
        return Result.success();
    }
    /**
     *
     * com.google.common.collect.Lists 工具包
     *
     */
    @RequestMapping(value = "collUtil")
    public void guava(){
        //快速创建List， 获得两个List的笛卡尔积 ， 生成二维List
        List<Integer> list = Lists.newArrayList(1,2,3);
        List<Integer> decor  = Lists.newArrayList(4,5);
        List<List<Integer>> decoreList = Lists.cartesianProduct(list,decor);
        log.info("decore: {}", JSONObject.toJSONString(decoreList));
        //创建二维List
        List<List<Integer>> two_list = Lists.newArrayList();
        List<Integer> in = Lists.newArrayList(1,2,3);
        List<Integer> in_two = Lists.newArrayList(4,5,6);
        two_list.add(in);
        two_list.add(in_two);
        System.out.println(two_list);   // [[1,2,3],[4,5,6]]
    }


}
