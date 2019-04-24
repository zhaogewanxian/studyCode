package com.wanxian.springcloud.demo;

import java.util.Observable;
import java.util.Observer;

public class ObserverDemo {

    public static void main(String[] args) {
        MyObservable observable = new MyObservable(); //声明发布组
        observable.addObserver(new Observer() {//添加订阅者 Observer
            @Override
            public void update(Observable o, Object value) {
                System.out.println(value);
            }
        });
        observable.setChanged();
        //发布者通知，订阅者被动感知(推模式)
        observable.notifyObservers("HelloWorld");
    }

    private static class MyObservable extends Observable {
        public void setChanged() {
            super.setChanged();
        }
    }
}
