package com.wanxian;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 冒泡排序 BubbleSort
 */
public class BubbleSort<T extends Comparable<T>> implements Sort<T> {


    @Override
    public void sort(T[] values) {
        //Comparable.compareTo
        //< return -1
        //= return = 0
        //> return 1
        int size = values.length;//数组长度
        for (int i = 0; i < size - 1; i++) {//第一层循环
            for (int j = 0; j < size - i - 1; j++) {//第二层循环
                if (values[j].compareTo(values[j + 1]) == 1) { //如果前一个元素比后一个大:低位>高位
                    T temp = values[j]; //临时存储变量 ,产生临时变量
                    values[j] = values[j+1];//调换位置
                    values[j+1] = temp; //赋值
                }
            }
            System.out.printf("第%d轮：%s\n", i + 1, Arrays.toString(values));
        }
    }

    public static void main(String[] args) {
        Integer[] values = Sort.of(2, 1, 5, 3, 4);
        //Integer[] values = Sort.of(5, 4, 3, 2, 1);
        Sort<Integer> sort = new BubbleSort<>(); //java 7 diamond语法
        sort.sort(values);
        Stream.of(values).forEach(System.out::println);
    }
}
