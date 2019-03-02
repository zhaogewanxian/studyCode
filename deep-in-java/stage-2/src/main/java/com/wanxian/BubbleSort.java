package com.wanxian;

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
        for (int i = 0; i < size; i++) {//第一层循环
            T temp = values[i]; //临时存储变量 ,产生临时变量
            for (int j = i + 1; j < size; j++) {//第二层循环
                if (temp.compareTo(values[j]) == 1) { //如果前一个元素比后一个大:低位>高位
                    values[i] = values[j];//调换位置
                    values[j] = temp; //赋值
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] values = Sort.of(2, 1, 5, 3, 4);
        Sort<Integer> sort = new BubbleSort<>(); //java 7 diamond语法
        sort.sort(values);
        Stream.of(values).forEach(System.out::println);
    }
}
