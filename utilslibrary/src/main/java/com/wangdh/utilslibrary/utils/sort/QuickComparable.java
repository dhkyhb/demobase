package com.wangdh.utilslibrary.utils.sort;

/**
 * Created by wangdh on 2018/5/29.
 * 快速排序 子类实现
 */

public abstract class QuickComparable<T> {
    //String[] strings = list.toArray(new String[list.size()]);
//List<String> list = Arrays.asList(arr);
    protected abstract int compareTo(T o);

    public void sort(T[] num, int left, int right) {
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
    protected int partition(T[] num, int left, int right) {
        if (num == null || num.length <= 0 || left < 0 || right >= num.length) {
            return 0;
        }
        T t = num[left + (right - left) / 2];//获取数组中间元素的下标
        int prio = compareTo(t);
        while (left <= right) {                 //从两端交替向中间扫描
            while (compareTo(num[left]) < prio)
                left++;
            while (compareTo(num[right]) > prio)
                right--;
            if (left <= right) {
                swap(num, left, right);        //最终将基准数归位
                left++;
                right--;
            }
        }
        return left;
    }

    protected void swap(T[] num, int left, int right) {
        T r = num[right];
        num[right] = num[left];
        num[left] = r;
    }

    public T[] sort(T[] num) {
        sort(num, 0, num.length - 1);
        return num;
    }
}
