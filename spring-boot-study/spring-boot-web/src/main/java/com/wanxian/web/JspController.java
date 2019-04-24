package com.wanxian.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("jsp")
@Controller
public class JspController {

    @RequestMapping("index")
    public String index(Model model) {
        model.addAttribute("massage", "wanxian");
        return "index";
    }


}
