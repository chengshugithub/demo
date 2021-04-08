package com.example.demo.controller;

import com.example.demo.mapper.TableDao;
import com.example.demo.service.impl.TableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/table")
public class TableController {

    @Resource
    private TableDao tableDao;
    @Autowired
    private TableServiceImpl tableServiceImpl;

    @RequestMapping("/list")
    public Map<String,Object> list()  throws Exception{
        Map<String,Object> result = new HashMap<>();
        result.put("tableList",tableServiceImpl.getTables());
        return result;
    }

    @RequestMapping("/getTableField")
    public Map<String,Object> getTableFieldByTableName(String tableName)  throws Exception{
        Map<String,Object> result = new HashMap<>();
        result.put("tableField",tableServiceImpl.getTableFieldByTableName(tableName));
        return result;
    }


    //jdbc调用

    @RequestMapping("/getTableFiles")
    public Map<String,Object> getTableFiles(String tableName)  throws Exception{
        Map<String,Object> result = new HashMap<>();
        result.put("tableField",tableServiceImpl.getTableFiles(tableName));
        return result;
    }
    @RequestMapping("/getTableNames")
    public Map<String,Object> getTableNames()  throws Exception{
        Map<String,Object> result = new HashMap<>();
        result.put("tableNames",tableServiceImpl.getTableNames());
        return result;
    }



}
