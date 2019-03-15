package com.wangdh.demolist;

import com.wangdh.demolist.annotation.demo.Demo1Annotation;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_1() {
        Demo1Annotation demo1Annotation = new Demo1Annotation();
        Method[] d = demo1Annotation.getClass().getDeclaredMethods();
        for (Method method : d) {
            System.out.println(method.getName());
            try {
//              method.getClass().getDeclaredMethod(method.getName());
                method.invoke(demo1Annotation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(d == null);
    }

    @Test
    public void test_2() {

    }
}