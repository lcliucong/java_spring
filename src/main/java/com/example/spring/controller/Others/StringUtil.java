package com.example.spring.controller.Others;

import com.example.spring.constant.ErrorEnum;
import com.example.spring.exception.ErrorException;
import com.example.spring.utils.Result;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RestController
@RequestMapping("stringUtils")
public class StringUtil {

    /**
     * 字符串判断
     */
    @RequestMapping(value = "determine", method = RequestMethod.POST)
    public Result determine(@RequestBody Map<String,?> params) throws ErrorException {
        String str = String.valueOf(params.get("str"));
        if (StringUtils.isAllBlank(str)) throw new ErrorException(ErrorEnum.COMMON_ERROR.getCode(), ErrorEnum.COMMON_ERROR.getMessage());
        List<String> lts2 = Lists.newArrayList(StringUtils.split(str, " "));
        //是否以指定内容开始,可以多个字符
        boolean startSpace = StringUtils.startsWithIgnoreCase(str,"s");
        //是否以指定内容结束，可以多个字符
        boolean endSpace = StringUtils.endsWithIgnoreCase(str,"是什么");
        return Result.success(endSpace);
    }

    /***
     * 字符串操作工具
     */
    @RequestMapping(value = "stringContro", method = RequestMethod.POST)
    public Result stringContro(@RequestBody Map<String,?> params) throws ErrorException {
        String oldStr = String.valueOf(params.get("oldStr"));
        String newStr = String.valueOf(params.get("newStr"));
        String str = "this is a string,这是一个字符串";
        //查找并替换指定子串replace("字符串",  "要被替换的字符串", "替换的值")
        String replaceStr = StringUtils.replace(str, oldStr, newStr);

        return Result.success(replaceStr);
    }
    @RequestMapping(value = "readFileTString",method = RequestMethod.POST)
    public Result readFileToString(@RequestParam("uploadFile") MultipartFile uploadFile) throws IOException {
        String originFileName = uploadFile.getOriginalFilename();  //上传文件本地名称  如 测试.docx
        //保存路径
        Path path = Paths.get("D:/java_project/spring/src/main/java/com/example/spring/files/"+originFileName);
        //移动到保存路径中
        uploadFile.transferTo(path);
        InputStream b = uploadFile.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(b));
        StringBuilder content = new StringBuilder();
        String line="";
        while((line = reader.readLine())!=null){
            content.append(line);
            log.info("content_asc: {}", content);
        }
        log.error("file_info_reverse: {}", content.reverse());
        return Result.success(content);
    }
    @RequestMapping(value = "StringTest", method = RequestMethod.POST)
    public Result StringTest(@RequestBody Map<String, Object> params){

        return Result.success();
    }








}
