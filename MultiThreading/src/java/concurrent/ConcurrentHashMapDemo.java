package concurrent;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;


public class ConcurrentHashMapDemo {

    private ConcurrentHashMap<String,Object> concurrentHashMap = new ConcurrentHashMap();
    private HashMap<String,Object> hashMap = new HashMap<>();
    private Hashtable<String,Object> hashtable = new Hashtable<>();

    private CountDownLatch latch ;

    public long testConHashMapInPut() throws InterruptedException {
        latch = new CountDownLatch(100);
        long start = System.currentTimeMillis();
        for (int i=0;i<100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<10000;i++){
                        concurrentHashMap.put(randomString(8),new Object());
                    }
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        return  System.currentTimeMillis()-start;
    }

    public long testHashMapInPut() throws InterruptedException {
        latch = new CountDownLatch(100);
        long start = System.currentTimeMillis();
        for (int i=0;i<100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<10000;i++){
                        synchronized (hashMap){
                            hashMap.put(randomString(8),new Object());
                        }
                    }
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        return  System.currentTimeMillis()-start;
    }

    public long testHashTableInPut() throws InterruptedException {
        latch = new CountDownLatch(100);
        long start = System.currentTimeMillis();
        for (int i=0;i<100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<10000;i++){
                        hashtable.put(randomString(8),new Object());
                    }
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        return  System.currentTimeMillis()-start;
    }

    public String randomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    private  final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >=1024) ? 1024 : n + 1;
    }

    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMapDemo demo = new ConcurrentHashMapDemo();
        System.out.println("ConcurrentHashMap Put 耗时:"+demo.testConHashMapInPut());
        System.out.println("ConcurrentHashMap Size:"+demo.concurrentHashMap.size());
        System.out.println("HashMap加锁 Put 耗时:"+demo.testHashMapInPut());
        System.out.println("HashMap Size:"+demo.hashMap.size());
        System.out.println("HashTable Put 耗时:"+demo.testHashTableInPut());
        System.out.println("HashTable Size:"+demo.hashtable.size());
    }
}
