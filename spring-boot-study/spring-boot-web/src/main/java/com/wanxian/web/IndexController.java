package com.wanxian.web;

import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "<html><body>sdfsdf</body></html>";//定义为@Controller时,查找模版,@RestController为@Response+@Controller
    }

    //    @RequestMapping(path = {"/test", "/test1"},method ={RequestMethod.POST,RequestMethod.GET}) 可以定义多种请求方式post,get
//   @GetMapping("/test2")
    @PostMapping("/test3")
    public String test() {
        return "<html><body>sdfsdf</body></html>";//定义为@Controller时,查找模版,@RestController为@Response+@Controller
    }


    @GetMapping("/test/{value}")
    public String test(@PathVariable String value) {
        return "<html><body>" + value + "</body></html>";//定义为@Controller时,查找模版,@RestController为@Response+@Controller
    }


}
