package com.wanxian.collection;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import static java.lang.System.out;

public class ThreadSafeCollection {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.set(2,0);//可修改值 Arrays.asList()非线程安全
        list.forEach(out::println);

        /**
         * of 实现都是不变的对象
         */
        list = List.of(1, 2, 3, 4, 5);
        Set<Integer> set = Set.of(1, 2, 3, 4, 5);
        Map<Integer, String> map = Map.of(1, "1a");


        //普通 List、Set 以及 Map 转化为线程安全对象
        // 通过 Collections#sychronized* 方法返回
        // Wrapper 设计模式（所有的方法都被 synchronized 同步或互斥）
        // 1.Java < 5 , Collections#synchronizedList
        // 2.Java 5+  , CopyOnWriteArrayList
        // 3.Java 9+  , List.of(...) 只读
        list = Collections.synchronizedList(list);
        set = Collections.synchronizedSet(set);
        map = Collections.synchronizedMap(map);
        list = new CopyOnWriteArrayList(list);
        set = new CopyOnWriteArraySet<>(set);
        map = new ConcurrentHashMap<>(map);



    }

}
