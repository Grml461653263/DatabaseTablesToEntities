package com.rain.controller;

import com.rain.dao.OracleTableToModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@RequestMapping("oracle")
public class CreateOracleController {

    @Resource
    OracleTableToModel oracleTableToMode;

    @GetMapping("create")
    @ResponseBody
    public ResponseEntity createModel(){
        oracleTableToMode.TableToModel();
        return ResponseEntity.ok("ok");
    }
}
