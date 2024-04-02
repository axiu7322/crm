package com.xxx.controller;


import com.xxx.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController extends BaseController {
    @RequestMapping("/testt")
    public String test(){
        return "/test/test";
    }
}
