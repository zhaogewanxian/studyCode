package com.wanxian.async;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "asyncServlet", urlPatterns = "/asyncServlet", asyncSupported = true) //开启异步
public class AsyncServlet extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html;charset=UTF-8");
        writer.println("线程" + Thread.currentThread().getName() + "异步开始.....");
        AsyncContext asyncContext = req.startAsync(req, resp);
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                writer.println("线程" + Thread.currentThread().getName() + "异步完成.....");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                writer.println("线程" + Thread.currentThread().getName() + "异步超时.....");

            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                writer.println("线程" + Thread.currentThread().getName() + "异步错误.....");

            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

            }


        });
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                writer.println("线程" + Thread.currentThread().getName() + "异步开始.....");
                asyncContext.complete();
            }
        });
        writer.println("线程" + Thread.currentThread().getName() + "异步结束了。。。。");

    }
}
