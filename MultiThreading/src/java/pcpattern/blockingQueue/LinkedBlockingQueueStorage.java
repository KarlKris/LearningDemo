package pcpattern.blockingQueue;

import pcpattern.AbstractStorage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingQueueStorage implements AbstractStorage {

    private final int SIZE = 100;
    private BlockingQueue queue;

    public LinkedBlockingQueueStorage() {
        queue = new LinkedBlockingDeque(SIZE);
    }

    @Override
    public void add(Object o) {
        try {
            System.out.println("【生产者】准备添加产品;此时仓库库存为->"+queue.size());
            queue.put(o);
            System.out.println("【生产者】添加成功；此时仓库库存为->"+queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove() {
        try {
            System.out.println("【消费者】准备处理产品;此时仓库库存为->"+queue.size());
            queue.take();
            System.out.println("【消费者】处理产品成功;此时仓库库存为->"+queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
