package com.wanxian.spring.mvc.servlet;

import com.wanxian.spring.annotation.Controller;
import com.wanxian.spring.annotation.RequestMapping;
import com.wanxian.spring.annotation.RequestParam;
import com.wanxian.spring.aop.ProxyUtils;
import com.wanxian.spring.context.ApplicationContext;
import com.wanxian.spring.mvc.HandlerAdapter;
import com.wanxian.spring.mvc.HandlerMapping;
import com.wanxian.spring.mvc.ModelAndView;
import com.wanxian.spring.mvc.ViewResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DispatchServlet extends HttpServlet {
    private Properties properties = new Properties();
    private Map<String, Object> beanMap = new ConcurrentHashMap<String, Object>();
    private List<String> classNames = new ArrayList<String>();
    private final String location = "contextConfigLocation";
    private Map<String, HandlerMapping> handlerMappingMap = new HashMap<String, HandlerMapping>();
    //springmvc 核心设计
    private List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
    private Map<HandlerMapping, HandlerAdapter> handlerAdapters = new HashMap<HandlerMapping, HandlerAdapter>();
    private List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("<font size='25' color='blue'>500 Exception</font><br/>Details:<br/>" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "")
                    .replaceAll("\\s", "\r\n") + "<font color='green'><i>Copyright@wanxian</i></font>");
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("开始初始化：init");
        ApplicationContext applicationContext = new ApplicationContext(config.getInitParameter(location));
        initStrategies(applicationContext);

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
        //解决页面名字和模板文件关联的问题
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        //URL url = this.getClass().getClassLoader().getResource(packName.replace(".", "/"));//转换成文件路径，找到文件目录、文件
        //File classDir = new File(url.getFile());
        File templateRootDir = new File(templateRootPath);

        for (File template : templateRootDir.listFiles()) {
            this.viewResolvers.add(new ViewResolver(template.getName(), template));
        }

    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {

    }

    private void initHandlerAdapters(ApplicationContext context) {
        //在初始化阶段，我们能做的就是，将这些参数的名字或者类型按一定的顺序保存下来
        //因为后面用反射调用的时候，传的形参是一个数组
        //可以通过记录这些参数的位置index,挨个从数组中填值，这样的话，就和参数的顺序无关了
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            Map<String, Integer> paramMapping = new HashMap<String, Integer>();
            Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
            for (int i = 0; i < pa.length; i++
            ) {
                for (Annotation a : pa[i]) {
                    if (a instanceof RequestParam) {
                        String paramName = ((RequestParam) a).value();
                        if (!"".equals(paramName.trim())) {
                            paramMapping.put(paramName, i);
                        }
                    }
                }
            }
            //处理非命名参数
            Class<?>[] paramTypes = handlerMapping.getMethod().getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++
            ) {
                Class<?> type = paramTypes[i];
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramMapping.put(type.getName(), i);
                }
            }
            this.handlerAdapters.put(handlerMapping, new HandlerAdapter(paramMapping));
        }


    }


    //用来保存Controller中配置的RequestMapping和Method的一个对应关系
    private void initHandlerMappings(ApplicationContext context) {
        try {
            //从容器中取到所有的实例
            String[] beanNames = context.getBeanDefinitionNames();

            for (String beanName :
                    beanNames) {
                Object proxy = context.getBean(beanName);
                Object controller = ProxyUtils.getTargetObject(proxy);
                Class<?> clazz = controller.getClass();
                //不是controller,直接返回
                if (!(clazz.isAnnotationPresent(Controller.class))) {//
                    continue;
                }
                String baseUrl = "";
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                    baseUrl = requestMapping.value();
                }
                //扫描所有的public方法
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(RequestMapping.class)) {
                        continue;
                    }

                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    String regex = ("/" + baseUrl + requestMapping.value().replaceAll("\\*", ".*")).replaceAll("/+", "/");
                    Pattern pattern = Pattern.compile(regex);
                    this.handlerMappings.add(new HandlerMapping(controller, method, pattern));
                    System.out.println("Mapping: " + regex + " , " + method);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (handler == null) {
            response.getWriter().write("404 Not Found \r\n@wanxian");
            return;
        }
        HandlerAdapter ha = getHandlerAdapter(handler);
        ModelAndView mv = ha.handle(request, response, handler);
        processDispatchResult(response, mv);

    }

    private void processDispatchResult(HttpServletResponse response, ModelAndView mv) throws Exception {
        if (mv == null) return;
        if (this.viewResolvers.isEmpty()) return;
        for (ViewResolver viewResolver :
                this.viewResolvers) {

            if (!mv.getViewName().equals(viewResolver.getName())) continue;

            String out = viewResolver.viewResolver(mv);
            if (null != out) {
                response.getWriter().write(out);
            }

        }
    }

    //参数动态处理，类型转换
    private HandlerAdapter getHandlerAdapter(HandlerMapping handler) {
        if (this.handlerAdapters.isEmpty()) return null;
        return handlerAdapters.get(handler);
    }

    private HandlerMapping getHandler(HttpServletRequest request) {
        if (this.handlerMappings.isEmpty()) return null;
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            Matcher matcher = handlerMapping.getUrl().matcher(url);
            if (matcher.matches()) {
                return handlerMapping;
            }
        }

        return null;

    }
}
