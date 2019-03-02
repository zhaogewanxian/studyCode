package com.wanxian;


import java.util.stream.Stream;

/**
 * 插入排序
 */
public class InsertionSort<T extends Comparable<T>> implements Sort<T> {

    @Override
    public void sort(T[] values) {
        //Comparable.compareTo
        //< return -1
        //= return = 0
        //> return 1
        int size = values.length;//数组长度
        for (int i = 1; i < size; i++) { //第一层循环
            T temp = values[i]; //高位数
            for (int j = i - 1; j >= 0; j--) { //第二层循环
                if (temp.compareTo(values[j]) < 1) {//低位>高位
                    values[i] = values[j]; //高位 获取低位的值
                    values[j] = temp; //低位获取高位的值
                }
            }
        }
    }


    public static void main(String[] args) {
        Integer[] values = Sort.of(2, 1, 5, 3, 4);
        Sort<Integer> sort = new InsertionSort<>(); //java 7 diamond语法
        sort.sort(values);
        Stream.of(values).forEach(System.out::println);
    }
}