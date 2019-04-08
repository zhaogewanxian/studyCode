package com.wanxian.thread;

import java.io.IOException;

public class ProccessCreation {
    public static void main(String[] args) throws IOException {
        //获取java Runtime
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("");
        process.exitValue();
    }
}
