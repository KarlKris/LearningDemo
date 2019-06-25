package com.Singleton_Pattern;


import Singleton_Pattern.SingletonHuman;

public class SingletonPatternTest {

    public static void main(String[] args) {
        SingletonHuman singletonHuman = null;
        for (int i = 0 ; i<10 ;i++){
            singletonHuman= SingletonHuman.getInstance();
            System.out.println(singletonHuman.getNo());
        }
    }
}
