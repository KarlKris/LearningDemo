package yeild;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.yield;

public class ThreadYeild implements Runnable{



    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread()+"开始执行");
            yield();
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread()+"完成执行");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread()+"线程被中断");
            e.printStackTrace();
        }

    }
}
