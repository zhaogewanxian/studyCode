package com.wanxian.demo.controller;


import com.wanxian.demo.service.IDemoService;
import com.wanxian.spring.annotation.Autowried;
import com.wanxian.spring.annotation.Controller;
import com.wanxian.spring.annotation.RequestMapping;
import com.wanxian.spring.annotation.RequestParam;
import com.wanxian.spring.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class TestController {

    @Autowried
    private IDemoService demoService;

    @RequestMapping("/query.json")
    public ModelAndView query(HttpServletRequest req, HttpServletResponse resp,
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

    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request,HttpServletResponse response,
                              @RequestParam("name") String name,@RequestParam("addr") String addr){
        String result = demoService.get(name);
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("teacher", name);
        return new ModelAndView("first.html",model);
    }

    @RequestMapping("/edit.json")
    public void edit(HttpServletRequest req, HttpServletResponse resp, Integer id) {

    }

    private ModelAndView out(HttpServletResponse resp,String str){
        try {
            resp.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
