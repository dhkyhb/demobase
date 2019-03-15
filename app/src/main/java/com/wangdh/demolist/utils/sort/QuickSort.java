package com.wangdh.demolist.utils.sort;

/**
 * Created by wangdh on 2018/5/29.
 * 快速递归排序demo 最快
 */

public class QuickSort {
    void sort(int num[], int left, int right) {
        if (left < right) {
            int index = partition(num, left, right); //算出枢轴值
            sort(num, left, index - 1);       //对低子表递归排序
            sort(num, index + 1, right);        //对高子表递归排序
        }
    }

    /**
     * 调用partition(num,left,right)时，对num[]做划分，
     * 并返回基准记录的位置
     *
     * @param num
     * @param left
     * @param right
     * @return
     */
    public int partition(int[] num, int left, int right) {
        if (num == null || num.length <= 0 || left < 0 || right >= num.length) {
            return 0;
        }
        int prio = num[left + (right - left) / 2];   //获取数组中间元素的下标
        while (left <= right) {                 //从两端交替向中间扫描
            while (num[left] < prio)
                left++;
            while (num[right] > prio)
                right--;
            if (left <= right) {
                swap(num, left, right);        //最终将基准数归位
                left++;
                right--;
            }
        }
        return left;
    }


    public void swap(int[] num, int left, int right) {
        int temp = num[left];
        num[left] = num[right];
        num[right] = temp;
    }

    public static void main(String args[]) {
        int[] num = {7, 3, 5, 1, 2, 8, 9, 2, 6};
        new QuickSort().sort(num, 0, num.length - 1);
        for (int n : num) {
            System.out.print(n + " ");
        }
    }
}