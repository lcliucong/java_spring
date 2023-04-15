package com.example.spring.controller.Others;

import com.alibaba.fastjson2.JSONObject;
import com.example.spring.constant.ErrorEnum;
import com.example.spring.daos.UserListMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.spring.pojo.UserList;
import com.example.spring.service.impl.UserListServiceImpl;
import com.example.spring.utils.CommonRes;
import com.example.spring.utils.ErrorMessage;
import com.example.spring.utils.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sun.tools.javac.Main;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "hel")
public class Hello {
    UserListMapper userListMapper;
    @Autowired
    private UserListServiceImpl userListServiceImpl;
    /**
     *  获取数据的几种方式
     *
     */
    //@PathVariable注解 常用于get
    @GetMapping("/hello/{name}/{password}")
    //比如name为xiao  password为123456 则路径为http://192.168.3.15:8080/hello/xiao/123456
    public  String hello(@PathVariable String name,
                         @PathVariable String password){
        return "name is "+name +"\n"+"password is "+password;
    }

    //@RequestParam注解 常用于get
    @GetMapping(value = "/reqparam")
    //name值username  password值123456  请求路径为http://192.168.3.15:8080/reqparam?username=1&password=2
    public String params(@RequestParam("username") Long username,
                         @RequestParam("password") String password){
        return "name is " + username + "\n" + "password is" +password;
    }

    //@RequestParam注解，使用post接收  并且标注某个参数不填写也不报错
    @RequestMapping(value = "reqPost", method = RequestMethod.POST)
    public CommonRes paramPost(@RequestParam(name = "param1", required = false, defaultValue = "10") String param1,
                               @RequestParam("param2") String param2,
                               @RequestParam String param3){
        return CommonRes.create();
    }

    public CommonRes setIfAbSent (@RequestParam (name = "keys", required = false) String keys, String values1, String values2, String values3){
//        Long res = stringRedisTemplate.opsForSet().add(keys,values1,values2,values3);
        log.info("keys: {}, values1: {}, values2: {}, values3: {}",keys,values1,values2,values3);
        return CommonRes.create("");
    }


//    @PathVariable和@RequestParam注解结合
    //结合方式 请求路径为http://192.168.3.15:8080/combine/1997?username=name&password=123456a
    @GetMapping(value = "/combine/{id}")
    public String combines(@PathVariable int id,
                           @RequestParam String username,
                           @RequestParam String password){
        return "id is "+id+"\n"+"username is "+username+"\n"+"password is " +password;
    }

//    @RequestBody注解 常用于post方法  需要使用json格式参数
//    @RequestMapping(value = "/postparam")
//    @ResponseBody
    //    一般被用来接收body中json数据，get、post都可以使用
//    public List<UserList> postparam(){
//
//        return userListServiceImpl.selectAll();
//
//    }

    //不参与任何注解 需要在form-data里传值  post与get都可以
    @RequestMapping(value = "none")
    @ResponseBody
    public String none(UserList userList){
        return userList.getUsername()+"\n"+userList.getPassword()+"\n"+userList.getPhone();
    }
    @RequestMapping(value = "string", method = RequestMethod.GET)
    public String strs(){
        String str = "hello world";
        int strlen = str.length();  //获取字符串长度 String.length();
        String con = " hello java";
        String con1 = str.concat(con);  //将两个字符串拼接起来
        char chars = str.charAt(1); //返回一个char类型的  指定索引位置的字符串
        int idx = str.indexOf("ll"); //返回int类型的，指定字符在字符串中的索引位置,加第二个参数时，表示从指定位置开始搜索
        int lastidx = str.lastIndexOf("l",5);
        return String.valueOf(lastidx);
    }
    @RequestMapping(value = "stringBuffer", method = RequestMethod.GET)
    public int strings() {
        //StringBuffer
        System.out.println();
        //获取字符串长度   String.length()
        String lens = "hello world";    //lens.length();
        //连接字符串   String.concat(字符串变量)
        String con = "my name is ";
        String con1 = con.concat(lens);
        //创建StringBuffer串(可以被修改的字符串)
        StringBuffer strBf = new StringBuffer("this is StringBuf");
        strBf.append(" String");   //将指定的字符串追加到StringBuffer串后边
//        strBf.reverse();    //字符串反转
        strBf.delete(17,9999);  //从指定位置移除字符串 delete('开始位置','结束位置')
        strBf.insert(0,999);  //将 int 类型参数插入到字符串中
        strBf.replace(17,21,"buffer");  //使用给定字符串 替换开始位置--结束位置的字符串
        int i =strBf.indexOf("i");
        return i;
    }
    @RequestMapping(value = "array")
    public int[] arrays(){
        //生成数组
        //第一种方法
        //dataType[] arrayRefVar = new dataType[arraySize];
        int[] arr = new int[3];
        //第二种方法
        //dataType[] array = {value, values};
        String[] arrs = {"test","test1","test2"};
        int[] intarr = {1,2,3,4,5,6};
        int leng = intarr.length;  //获取数组长度    //数组.length
        int[] arr1 = {1,2,3,4,6};
        return arr1;
    }

    /**
     * 通过json格式获取RequestBody
     */
    @RequestMapping(value = "getMark", method = RequestMethod.POST)
    public Result getMark(@RequestBody String json) {
        JSONObject par = JSONObject.parseObject(json);
        System.out.println(par.getString("money"));
        System.out.println(par.getString("remark"));
        return Result.success(par);
    }

    /**
     * 通过map形式获取RequestBody
     */
    @RequestMapping(value = "getRequestBody", method = RequestMethod.POST)
    public Result RequestBodys(@RequestBody Map<?,?> paramMap){
        paramMap.forEach((k,v)->{
            log.info("values: {} ", paramMap.get(k));
            log.info("values: {} ", paramMap.get("money"));
//            log.info("values: {} ", paramMap.get("参数名称"));
        });
        return Result.success(paramMap);
    }
    /**
     * {
     *  String jsonParam = {\"phone\":\"159334452\",\"sex\":\"男\",\"name\":\"test\"}
     * }
     * 提供String 类型的获取到的data值，和 要获取的字段名称
     */
    public String JsDecode(String jsonParam, String keyName){
        JSONObject param = JSONObject.parseObject(jsonParam);
        return param.getString(keyName);
    }

    /**
     * 当参数像这样时，可以这样获取
     * {
     *     "key":"tester",
     *     "name":"sena",
     *     "phone":"1593344",
     *     "sex":1,
     *     "group":{
     *         "group_id":1008611,
     *         "group_level":"heigh"
     *     }
     * }
     */
    @RequestMapping(value = "listToJson", method = RequestMethod.POST)
    public Result makeJson(@RequestBody String jsonParam){
        JSONObject objParam = JSONObject.parseObject(jsonParam);
        String key =  objParam.getString("key");
        String name = objParam.getString("name");
        JSONObject param = objParam.getJSONObject("group");
        log.info("group_id : {}", param.get("group_id"));
        return Result.success(param.get("group_level"));
    }
    /**
     * pretty print
     * 全局打印
     */
    @RequestMapping(value = "prettyPrint1")
    public Result prettyPrint(@RequestBody String jsonParam) throws JsonProcessingException {
//        import com.fasterxml.jackson.databind.ObjectMapper;
//        import com.fasterxml.jackson.databind.SerializationFeature;

        ObjectMapper mapper = new ObjectMapper();
        // pretty print jsonParam 为一个json对象
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParam);
        System.out.println(json);
        return Result.success();
    }
    /**
     * pretty print
     * 局部打印
     */
    @RequestMapping(value = "prettyPrint", method = RequestMethod.POST)
    public Result prettyPrintLocal(@RequestBody String jsonParam) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // pretty print   jsonParam 为一个json对象
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParam);
        System.out.println(json);
        return Result.success(json);
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
            throw new ErrorMessage(ErrorEnum.COMMON_ERROR.getCode(), ErrorEnum.COMMON_ERROR.getErrMessage());
        }
        List<String> strList = Lists.newArrayList(
                "element",
                ""
        );
        return Result.success(list);
    }
}
