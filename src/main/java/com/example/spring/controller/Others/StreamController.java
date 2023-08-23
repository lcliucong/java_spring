package com.example.spring.controller.Others;

import com.example.spring.exception.ErrorException;
import com.example.spring.utils.Result;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * stream流相关
 */
@Log4j2
@RestController
@RequestMapping("stream")
public class StreamController {


    @RequestMapping(value = "makeStream", method = RequestMethod.POST)
    public Result makeStream() throws ErrorException {
        ArrayList<String> list = new ArrayList<>();
        //批量添加数据到list中
        Collections.addAll(list, "String", "Arrays", "Banana", "Apple", "Alpha", "Bmw");
        list.stream().filter(s -> s.startsWith("A"))
                .filter(s -> s.length() == 5)
                .forEach(System.out::println);


        return Result.success();
    }


    public static String changeMysqlTableToClickHouse(String tableName) {
        String tables = tableName;
        String primaryKey = "`id`";//默认id
        String[] rows = tables.split("\n");
        StringBuilder replaceTables = new StringBuilder();
        Boolean haveKey = false;
        int i = 0;
        for (String row : rows) {
            // 注释，不处理
            if (row.contains("--")) {
                replaceTables.append(row + "\n");
                continue;
            }
            if (row.contains("KEY")) {
                if (row.contains("PRIMARY")) {
                    haveKey = true;
                    primaryKey = row.substring(row.indexOf("(") + 1, row.indexOf(")"));
                }
                // 跳过、不添加
                continue;
            }
            if (row.contains("ENGINE=InnoDB")) {
                // 引擎替换
                row = ") ENGINE = ReplacingMergeTree";
            }

            // 无关删除
            String changeRow = row.replaceAll("AUTO_INCREMENT", "")
                    .replaceAll("CHARACTER SET utf8mb4", "")
                    .replaceAll("CHARACTER SET utf8", "")
                    .replaceAll("ON UPDATE CURRENT_TIMESTAMP", "")
                    .replaceAll("CURRENT_TIMESTAMP", "")
                    .replaceAll("( DEFAULT CHARSET).*", "")
                    // 时间替换
                    .replaceAll("datetime DEFAULT NULL", " DateTime ")
                    .replaceAll(" datetime ", " DateTime ");

            /*String规则*/
            // 为空,字符串
            changeRow = changeRow.replaceAll("(` ).*(char).*(DEFAULT NULL)", "` String NULL");
            changeRow = changeRow.replaceAll("(` ).*(char).*(DEFAULT '')", "` String");
            // changeRow = changeRow.replaceAll("(DEFAULT '')", "NULL");
            // 非空,字符串
            changeRow = changeRow.replaceAll("(` ).*(char).*(NOT NULL)", "` String");
            changeRow = changeRow.replaceAll("text", "String");
            changeRow = changeRow.replaceAll("(DEFAULT NULL)", "NULL");
            changeRow = changeRow.replaceAll("(NOT NULL)", "");

            // 以空格分割
            String[] changeColumns = changeRow.split("[ ]");
            //      System.out.println(changeRow);
            // 含有int的替换规则
            if (changeColumns[3].contains("int") || changeColumns[3].contains("bigint")
                    ||changeColumns[3].contains("INT")) {
                changeColumns[3].replaceAll("INT","int");
                changeColumns[3].replaceAll("BIGINT","bigint");
                // 将括号内的数字拿出来
                int length = Integer.parseInt(changeColumns[3]
                        .replaceAll("bigint", "")
                        .replaceAll("tinyint", "")
                        .replaceAll("int", "")
                        .replaceAll("\\(", "")
                        .replaceAll("\\)", ""));
                // 获取数据类型
                String type = changeColumns[3].substring(0, changeColumns[3].indexOf("("));
                // 处理int 是否可以为空值
                String last = " NULL";
                String[] _int = {"Int8", "Int16", "Int32", "Int64"};
                if (changeRow.contains("DEFAULT NULL")) {
                    changeRow = changeRow.replaceAll("DEFAULT NULL", "");
                    for (int j = 0; j < _int.length; j++) {
                        _int[j] = _int[j] + last;
                    }
                }
                if ("tinyint".equals(type)) {
                    changeRow = changeRow
                            .replaceFirst(type + "\\(" + length + "\\)", _int[0]);
                } else if ("smallint".equals(type)) {
                    changeRow = changeRow
                            .replaceFirst(type + "\\(" + length + "\\)", _int[1]);
                } else if ("int".equals(type) || "mediumint".equals(type)) {
                    changeRow = changeRow
                            .replaceFirst(type + "\\(" + length + "\\)", _int[2]);
                } else {
                    changeRow = changeRow
                            .replaceFirst(type + "\\(" + length + "\\)", _int[3]);
                }
            }

            replaceTables.append(changeRow.trim() + "\n");
            if (i == 0) {
                replaceTables.append("\n");
            }
            i++;
        }
        if (replaceTables.toString().contains(",) ENGINE = Memory")) {
            String temp = replaceTables.substring(0, replaceTables.indexOf(",) ENGINE = Memory"));
            replaceTables = new StringBuilder(temp + ") ENGINE = Memory ");
        }
        replaceTables.toString().replaceAll("CREATE TABLE `" + tableName + "`", tableName + "_local");
        if (haveKey) {
            replaceTables.append("PRIMARY KEY " + primaryKey);
        }
        replaceTables.append("\nORDER BY " + primaryKey);
        replaceTables.append(";");
        return replaceTables.toString();
    }
}
