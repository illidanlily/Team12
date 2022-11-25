package com.example.team12.controller;

import com.example.team12.service.serviceImpl.Httpinsertservicelmpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class Httpinsercontrller {
    @Resource
    Httpinsertservicelmpl httpinsertservicelmpl;

    @PostMapping(value = "/insertzhibo")//和ajax请求中url相对应
    public int updatezhibo(@RequestBody Map map){
        return httpinsertservicelmpl.addStreamline(map);
    }


}
