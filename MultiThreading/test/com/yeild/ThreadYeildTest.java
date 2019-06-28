package com.yeild;

import yeild.ThreadYeild;

/**
 * yeild()方法会告知CPU调度器，自己可以让出cpu时间片，重新进入竞争队列
 * cpu调度器可能会忽略这次告知。所以yeild()只是增加了其他线程执行的机会
 * 但并不保证自己一定会让出cpu时间片
 * （也许是cpu调度器忽略了或者自己重新竞争得到cpu时间片)
 */
public class ThreadYeildTest {

    public static void main(String[] args) {

        for (int i = 0 ;i<2 ; i++){
            new Thread(new ThreadYeild()).start();
        }
    }
}
