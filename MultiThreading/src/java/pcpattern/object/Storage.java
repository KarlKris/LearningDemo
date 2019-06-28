package pcpattern.object;

import pcpattern.AbstractStorage;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Storage implements AbstractStorage {

    private final int SIZE = 100;
    private List list;

    public Storage() {
        list = new LinkedList();
    }

    @Override
    public void add(Object o) throws InterruptedException {
        synchronized (list) {
            int size = SIZE - list.size();
            System.out.println("【生产者】"+Thread.currentThread().getName()+"仓库剩余量为->" + size);
            //仓库剩余量<=0 则仓库已满
            while (size <= 0) {
                try {
                    System.out.println("【生产者】"+Thread.currentThread().getName()+"仓库已满，等待消费者消费");
                    TimeUnit.SECONDS.sleep(2);
                    list.wait();
                } catch (InterruptedException e) {
                    throw e;
                }
                //再次判断
                size = SIZE - list.size();
            }
            Thread.sleep(1000);
            list.add(o);
            System.out.println("【生产者】"+Thread.currentThread().getName()+"添加一个产品；此时仓库库存为->" + list.size());
            list.notifyAll();
        }
    }

    @Override
    public void remove() throws InterruptedException {
        synchronized (list) {
            int size = list.size();
            System.out.println("【消费者】"+Thread.currentThread().getName()+"仓库库存为->" + size);
            while (size <= 0) {
                System.out.println("【消费者】"+Thread.currentThread().getName()+"仓库库存为0，等待生产者添加");
                list.wait();
                //再次判断条件
                size = list.size();
            }
            Thread.sleep(1000);
            list.remove(0);
            System.out.println("【消费者】"+Thread.currentThread().getName()+"处理一个产品；此时仓库库存为->" + list.size());
            list.notifyAll();
        }
    }

}
