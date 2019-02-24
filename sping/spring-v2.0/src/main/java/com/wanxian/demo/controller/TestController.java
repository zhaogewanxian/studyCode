package com.wanxian.demo.controller;


import com.wanxian.demo.service.IDemoService;
import com.wanxian.spring.annotation.Autowried;
import com.wanxian.spring.annotation.Controller;
import com.wanxian.spring.annotation.RequestMapping;
import com.wanxian.spring.annotation.RequestParam;
import com.wanxian.spring.mvc.ModleAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/demo")
public class TestController {

    @Autowried
    private IDemoService demoService;

    @RequestMapping("/query.json")
    public ModleAndView query(HttpServletRequest req, HttpServletResponse resp,
                              @RequestParam("name") String name) {
        String result = demoService.get(name);
        System.out.println(result);
//		try {
//			resp.getWriter().write(result);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        return null;
    }

    @RequestMapping("/edit.json")
    public void edit(HttpServletRequest req, HttpServletResponse resp, Integer id) {

    }

}
