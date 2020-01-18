package com.wangdh.utilslibrary.utils;


import com.apkfuns.logutils.LogUtils;

/**
 * @author wangdh
 * @date 2020/1/18 11:53
 * 描述: 日志
 */
public class Log {
    public static void init(){
        TLog.d("12345");
//        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public void test(){
// 输出字符串
        TLog.e("12345");

// 输出参数
//        TLog.d("12%s3%d45", "a", 0);

// 输出异常
        TLog.e(new NullPointerException("12345"));

// 输出对象
//        Person person = new Person();
//        person.setAge(11);
//        person.setName("pengwei");
//        person.setScore(37.5f);
//        TLog.d(person);

// 对象为空
        TLog.d(null);

// 输出json（json默认debug打印）
        String json = "{'a':'b','c':{'aa':234,'dd':{'az':12}}}";
        LogUtils.json(json);

// 打印数据集合
//        List<Person> list1 = new ArrayList<>();
//        for(int i = 0; i < 4; i++){
//            list1.add(person);
//        }
//        TLog.d(list1);

// 打印数组
        double[][] doubles = {{1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33}};
        TLog.d(doubles);

// 自定义tag
        LogUtils.tag("我是自定义tag").d("我是打印内容");

// 其他用法
        LogUtils.v("12345");
        LogUtils.i("12345");
        LogUtils.w("12345");
        LogUtils.e("12345");
        LogUtils.wtf("12345");
    }
}
