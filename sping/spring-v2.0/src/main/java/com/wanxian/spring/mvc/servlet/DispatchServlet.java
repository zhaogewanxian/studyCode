package com.wanxian.spring.mvc.servlet;

import com.wanxian.spring.context.ApplicationContext;
import com.wanxian.spring.mvc.HandlerAdapter;
import com.wanxian.spring.mvc.HandlerMapping;
import com.wanxian.spring.mvc.ModleAndView;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DispatchServlet extends HttpServlet {
    private Properties properties = new Properties();
    private Map<String, Object> beanMap = new ConcurrentHashMap<String, Object>();
    private List<String> classNames = new ArrayList<String>();
    private final String location = "contextConfigLocation";
    private Map<String,HandlerMapping>  handlerMappingMap = new HashMap<String, HandlerMapping>();
    //springmvc 核心设计
    private List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("开始初始化：init");
        ApplicationContext applicationContext = new ApplicationContext(config.getInitParameter(location));

    }


    protected void initStrategies(ApplicationContext context) {
        // initMultipartResolver(context);
        //initLocaleResolver(context);
        //initThemeResolver(context);
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        initViewResolvers(context);
        initFlashMapManager(context);
    }

    private void initFlashMapManager(ApplicationContext context) {
    }

    private void initViewResolvers(ApplicationContext context) {

    }
    private void initRequestToViewNameTranslator(ApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {

    }

    private void initHandlerAdapters(ApplicationContext context) {

    }


    //用来保存Controller中配置的RequestMapping和Method的一个对应关系
    private void initHandlerMappings(ApplicationContext context) {



    }

    private void initLocaleResolver(ApplicationContext context) {
    }


    private String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerMapping handler = getHandler(request);
        HandlerAdapter ha = getHandlerAdapter(handler);
        ModleAndView mv = ha.handle(request, response, handler);
        processDispatchResult(request, mv);

    }

    private void processDispatchResult(HttpServletRequest request, ModleAndView mv) {
    }
    //参数动态处理，类型转换
    private HandlerAdapter getHandlerAdapter(HandlerMapping handler) {
        return null;

    }

    private HandlerMapping getHandler(HttpServletRequest request) {
        return null;

    }
}
