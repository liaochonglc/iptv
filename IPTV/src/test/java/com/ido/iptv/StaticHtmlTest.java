package com.ido.iptv;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
@RestController
public class StaticHtmlTest {
    @Autowired
    public Template template;
@PostMapping("/user/testHtml")
    public  String test(Map model) throws Exception{
    System.out.println("------468464");
        String path = "/static/login.html";
        model.put("name","lc");
    String p=this.getClass().getResource("/").toURI().getPath()+"static/login.html";
    Writer file = new FileWriter(new File(p.substring(p.indexOf("/"))));
        template.process(model,file);
        return null;
    }
}
