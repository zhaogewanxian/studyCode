package com.wanxian.spring.mvc;


import java.io.File;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//将静态文件变为动态文件
public class ViewResolver {
    private String name;
    private File template;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getTemplate() {
        return template;
    }

    public void setTemplate(File template) {
        this.template = template;
    }

    public ViewResolver(String viewName) {

    }

    public ViewResolver(String name, File template) {
        this.name = name;
        this.template = template;
    }

    public String viewResolver(ModelAndView mv) throws Exception {
        StringBuffer sb = new StringBuffer();

        RandomAccessFile ra = new RandomAccessFile(this.template,"r");

        try {
            String line = null;
            while (null != (line = ra.readLine())) {
                line = new String(line.getBytes("ISO-8859-1"), "utf-8");
                Matcher m = matcher(line);
                while (m.find()) {
                    for (int i = 1; i <= m.groupCount(); i++) {

                        //要把￥{}中间的这个字符串给取出来
                        String paramName = m.group(i);
                        Object paramValue = mv.getModle().get(paramName);
                        if (null == paramValue) {
                            continue;
                        }
                        line = line.replaceAll("￥\\{" + paramName + "\\}", paramValue.toString());
                        line = new String(line.getBytes("utf-8"), "ISO-8859-1");
                    }
                }
                sb.append(line);
            }
        }finally {
            ra.close();
        }

        return sb.toString();
    }
    private Matcher matcher(String str){
        Pattern pattern = Pattern.compile("￥\\{(.+?)\\}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return  matcher;
    }

}
