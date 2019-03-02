package com.wanxian;

import java.util.stream.Stream;

/**
 * 快速排序
 */
public class QuickSort<T extends Comparable<T>> implements Sort<T> {
    @Override
    public void sort(T[] values) {

        int n = values.length;
        int low = 0;
        int high = n - 1;
        sort(values, low, high);

    }

    private void sort(T[] values, int low, int high) {
        if (low < high) {
            int pIndex = partition(values, low, high);
            sort(values, low, pIndex - 1);
            sort(values, pIndex + 1, high);
        }
    }

    int partition(T[] values, int low, int high) {
        T pivot = values[high]; //获取pivot 轴
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (values[j].compareTo(pivot) < 1) { //<=
                i++;
                T temp = values[i]; //低位数据
                values[i] = values[j];//低位数据获取高位数据
                values[j] = temp;
            }
        }
        T temp = values[i + 1];
        values[i + 1] = values[high];
        values[high] = temp;
        return i + 1;
    }


    public static void main(String[] args) {
        Integer[] values = Sort.of(3,1,2,5,4);
        Sort<Integer> sort = new QuickSort<>(); //java 7 diamond语法
        sort.sort(values);
        Stream.of(values).forEach(System.out::println);
    }
}
