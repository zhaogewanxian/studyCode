package com.wanxian.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Service;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import javax.net.ssl.StandardConstants;
import java.io.File;

/**
 * 嵌入式Tomcat服务器
 */
public class EmbeddedTomcatServer {
    public static void main(String[] args) throws Exception {
        String classesPath = System.getProperty("user.dir") + "/" + "target/class";
        System.out.printf(classesPath);
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(6060);//设置端口 6060
        Host host = tomcat.getHost();
        host.setName("localhost");
        host.setAppBase("webapp");
        String contextPath = "/";
        //静态文件直接在idea找
        String webapp = System.getProperty("user.dir") +
                File.separator + "src" + File.separator + "main" +
                File.separator + "webapp";
        Context context = tomcat.addWebapp(contextPath, webapp);
        if (context instanceof StandardContext) {
            StandardContext standardContext = (StandardContext) context;
            //设置默认web.xml文件到Context
            standardContext.setDefaultContextXml(webapp + File.separator + "conf/web.xml");
            //设置classPath到Context
            Wrapper wrapper = tomcat.addServlet(contextPath, "DemoServlet", new DemoServlet());
            wrapper.addMapping("/demo");
        }
        Service service = tomcat.getService();
        Connector connector= new Connector();
        connector.setPort(9090);
        connector.setProtocol("HTTP/1.1");
        connector.setURIEncoding("UTF-8");
        service.addConnector(connector);
        //启动
        tomcat.start();
        //强制Tomcat Server等待，避免main线程执行结束关闭，为什么不直接Object#wait()，sync
        tomcat.getServer().await();
    }
}
