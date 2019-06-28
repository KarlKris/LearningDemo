package com.Singleton_Pattern;


import singleton.SingletonHuman;

/**
 * 单例模式：无论多少次调用，都只返回同一个实例
 * 适用于工具类和不可变类和一个创建开销大（耗时长）的类
 */
public class SingletonPatternTest {

    public static void main(String[] args) {
        SingletonHuman singletonHuman = null;
        for (int i = 0 ; i<10 ;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(SingletonHuman.getInstance().getNo());
                }
            }).start();
        }
    }
}
