package com.join;

import join.ThreadJoin;

public class ThreadJoinTest {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread()+"开始");
        Thread thread = new Thread(new ThreadJoin());
        Thread thread1 = new Thread(new ThreadJoin());

        thread.start();
        thread1.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread()+"结束");

    }
}
