package com.lanswon.cloudgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author GU-YW
 * @Date 2019/9/28 11:26
 */
public class FailBackController {

    @GetMapping("fallback")
    public Map<String,Object> fallback(){
        Map<String,Object> map=new HashMap<>();
        map.put("message","服务暂时不可用");
        return map;
    }
}
