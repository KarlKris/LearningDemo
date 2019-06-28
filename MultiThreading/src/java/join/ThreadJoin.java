package join;

import java.util.concurrent.TimeUnit;

public class ThreadJoin implements Runnable{

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread()+"开始执行");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread()+"结束执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
