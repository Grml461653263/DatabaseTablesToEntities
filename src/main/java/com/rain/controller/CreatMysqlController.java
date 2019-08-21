package com.rain.controller;


import com.rain.dao.MysqlTableToModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("mysql")
public class CreatMysqlController {



    @Resource
    MysqlTableToModel mysqlTableToModel;

    @GetMapping("create")
    @ResponseBody
    public ResponseEntity createModel(){
        mysqlTableToModel.TableToModel();
        return ResponseEntity.ok("ok");
    }
}
