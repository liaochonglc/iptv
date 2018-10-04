package com.ido.iptv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TwoTest {
    @PostMapping("/a")
    //测试url重写
    public  String t(){
        return "login.html";
    }
}
