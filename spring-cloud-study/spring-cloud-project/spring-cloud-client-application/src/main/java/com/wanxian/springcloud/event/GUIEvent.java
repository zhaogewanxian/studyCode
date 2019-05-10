package com.wanxian.springcloud.event;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * java 事件/监听机制
 */
public class GUIEvent {
    public static void main(String[] args) {
        final JFrame jFrame = new JFrame("Java 事件/监听机制");
        jFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.printf("[%s]事件[%d]", Thread.currentThread().getName(), e);
            }
        });
        jFrame.setBounds(300, 300, 400, 400);
        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jFrame.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);//关闭时退出
            }
        });
    }
}
