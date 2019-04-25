package com.wanxian.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制器
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("xss")
    public String xss(Model model) {
        model.addAttribute("jsCode", "<script>alert('hahaha')</script>");
        model.addAttribute("htmlCode", "<span>hahahhahaff</span>");
        return "xss";
    }
}
