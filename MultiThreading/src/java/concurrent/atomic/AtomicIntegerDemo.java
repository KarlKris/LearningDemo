package concurrent.atomic;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicIntegerDemo {

    private final int COUNT = 100000;
    private AtomicInteger atomicInteger;
    private Integer anInt;
    private Integer seInt;
    private Object objectLock;
    private Lock lock;
    private CountDownLatch latch;

    public CountDownLatch getLatch() {
        return latch;
    }

    public AtomicIntegerDemo() {
        atomicInteger = new AtomicInteger(0);
        anInt = 0;
        seInt = 0;
        objectLock = new Object();
        lock = new ReentrantLock();
    }

    public long testInAtomicInteger() throws InterruptedException {
        latch = new CountDownLatch(COUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    atomicInteger.incrementAndGet();
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        return System.currentTimeMillis()-start;
    }

    public long testBySynchronized() throws InterruptedException {
        latch = new CountDownLatch(COUNT);
        long start = System.currentTimeMillis();
        for(int i=0;i<COUNT;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (objectLock) {
                        anInt++;
                    }
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        return System.currentTimeMillis()-start;
    }

    public long testByLock() throws InterruptedException {
        latch = new CountDownLatch(COUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    seInt++;
                    lock.unlock();
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        return System.currentTimeMillis()-start;
    }

    public static void main(String[] args) {
        int b = 32 ;
        System.out.println(b-(b>>>2));
        Object o = new Object();
        System.out.println();
        System.out.println(o.hashCode());
        System.out.println((o.hashCode()^(o.hashCode()>>>16))&0x7fffffff);
        AtomicIntegerDemo a = new AtomicIntegerDemo();
        try {
            System.out.println("------------------------AtomicInteger--------------------");
            long time = a.testInAtomicInteger();
            a.getLatch().await();
            System.out.println("耗时:" + time);
            System.out.println("结果："+a.atomicInteger);
            System.out.println("---------------------------------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       try{
           System.out.println("------------------------Synichronized--------------------");
           System.out.println("耗时:" + a.testBySynchronized());
           System.out.println("结果："+a.anInt);
           System.out.println("---------------------------------------------------------");
       }catch (InterruptedException e){
           e.printStackTrace();
       }

        try{
            System.out.println("------------------------Lock-----------------------------");
            System.out.println("耗时:" + a.testByLock());
            System.out.println("结果："+a.seInt);
            System.out.println("---------------------------------------------------------");
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
