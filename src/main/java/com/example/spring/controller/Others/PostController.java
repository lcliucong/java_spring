package com.example.spring.controller.Others;

import com.alibaba.fastjson2.JSONObject;
import com.example.spring.utils.CurlUtil;
import com.example.spring.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "post")
@Slf4j
@Component
@RestController
public class PostController {

    @RequestMapping(value = "post")
    public Result sendPost(@RequestParam(name = "param1",defaultValue = "tester")String param1){
        Map<Object, String> map = new HashMap<>();
        map.put("param1",param1);
        Map<String, String> headers = new HashMap<>();
        headers.put("session_key","session_key_7000210164127581b7");
        headers.put("special_id","164127581b7");
        headers.put("pid","mm_10086_10064");
        String uri = "http://192.168.3.15/api/64255c9d7f46f";
        //获取json类型的数据
        ResponseEntity<String> st = CurlUtil.post(uri,map,headers);
        JSONObject obj = JSONObject.parseObject(st.getBody());
        assert obj != null;
        JSONObject obj_data = obj.getJSONObject("data");
        log.info("param1 : {} ", obj_data.get("param1"));
        System.out.println("obj_data: "+obj_data);
        return Result.success(obj);
    }
    @RequestMapping(value = "getMark", method = RequestMethod.POST)
    public Result getMark(@RequestBody String jsonParam){
        StringBuilder uri = new StringBuilder("http://192.168.3.15/api/5e3fb0e0c0988?");
//        第一种接收@RequestBody方式   (@RequestBody String jsonPamam)
        JSONObject par = JSONObject.parseObject(jsonParam);
        par.getString("money");
        par.getString("remark");
        uri.append("money=").append(par.get("money")).append("&key=").append(par.get("key")).append("&remark=").append(par.get("remark"));
//        第二种接收@RequestBody方式   (@RequestBody Map<String,String> paramMap)
//        params.forEach((k,v)->{
//            log.error("values : {} ", params.get(k));
//            uri.append("&").append(k).append("=").append(params.get(k));
//        });
        System.out.println(uri);
//        Map<String,String> headerMap = new HashMap<>();
//        headerMap.put("Keep-Alive","true");
        ResponseEntity<String> obj = CurlUtil.get(uri.toString());
        JSONObject res = JSONObject.parseObject(obj.getBody());
        assert res != null;
        String money = res.getString("money");
        log.info("res: {}",res);
        log.error("data-money : {} ",money);
        return Result.success(obj);
    }
    @RequestMapping(value= "get", method = RequestMethod.POST)
    public Result get(@RequestParam HashMap<Object, String> paramMaps)
    {
        Map<Object,String> maps = new HashMap<>();
        for (Object keys : paramMaps.keySet()){
            maps.put(keys,paramMaps.get(keys));
        }
        log.info("getname: {}", maps.get("name"));
        System.out.println(maps);
        return Result.success(maps);
    }
}
