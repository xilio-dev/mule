package cn.xilio.project.site.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("post")
@RestController
public class PostController {
    @PostMapping("list")
    public Object list(@RequestBody Object o){
        return "hello world";
    }
    
    @PostMapping("add")
    public Object add(@RequestBody Object o){
        return "hello world";
    }
    
    @PostMapping("detail")
    public Object detail(@RequestBody Object o){
        return "hello world";
    }
    
}
