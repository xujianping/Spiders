package com.kedian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author happy
 * @version 0
 * @Package com.kedian.controller
 * @date 2018/6/11 14:18
 * @Description:
 */
@Controller
@RequestMapping("/test")
public class test {
    @RequestMapping("/")
    public String index(){
        return  "test";
    }

}
