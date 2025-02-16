package cn.xilio.project.site.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${moduleName}")
@RestController
public class ${moduleName?capitalize}Controller {
<#list apiNames as apiName>
    @PostMapping("${apiName}")
    public Object ${apiName}(@RequestBody Object o){
        return "hello world";
    }
    
</#list>
}
