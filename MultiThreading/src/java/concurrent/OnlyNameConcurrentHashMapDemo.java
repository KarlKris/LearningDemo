package concurrent;

import com.sun.org.apache.xpath.internal.functions.Function;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class OnlyNameConcurrentHashMapDemo {

    private   ConcurrentHashMap<String,String> map;

    public OnlyNameConcurrentHashMapDemo() {
        this.map = new ConcurrentHashMap();
    }

    public boolean check(String name){
        return map.containsKey(name);
    }

    public boolean checkAndPut(String name){
        Object obj = map.putIfAbsent(name,"null");
        return obj==null ? true : false;
    }

    public boolean put(String name,int time){
        if (checkAndPut(name)){
            try {
                String no = build(name,time);
                System.out.println(no);
                String str = map.put(name,no);
                return "null".equals(str);
            } catch (InterruptedException e) {
                if (check(name)){
                    map.remove(name);
                }
                return false;
            }
        }
        return false;
    }

    public String  build(String name,int time) throws InterruptedException {
        TimeUnit.SECONDS.sleep(time);
        return Thread.currentThread().getName();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CountDownLatch latch = new CountDownLatch(10);
        OnlyNameConcurrentHashMapDemo demo = new OnlyNameConcurrentHashMapDemo();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Future<Boolean>> resList = new ArrayList<>();
        for (int i=0;i<10;i++){
            resList.add(executor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    Random random = new Random();
                    int time = random.nextInt(5);
                    boolean flag = demo.put("abcd",time);
                    latch.countDown();
                    return flag;
                }
            }));
        }
        latch.await();
        System.out.println("-------------------------------------------------------");
        for (Future<Boolean> future : resList){
            System.out.println(future.get());
        }
        System.out.println("-------------------------------------------------------");
        System.out.println(demo.map.size());
        System.out.println(demo.map.get("abcd"));
    }

}
