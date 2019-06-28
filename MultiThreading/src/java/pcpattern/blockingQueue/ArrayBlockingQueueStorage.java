package pcpattern.blockingQueue;

import pcpattern.AbstractStorage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingQueueStorage implements AbstractStorage {

    private final int SIZE = 100;
    private BlockingQueue queue;

    public ArrayBlockingQueueStorage() {
        queue = new ArrayBlockingQueue(SIZE);
    }

    @Override
    public void add(Object o) {
        try {
            System.out.println("【生产者】准备添加产品;此时仓库库存为->"+queue.size());
            /**
             * add（）:队列未满时，返回true，满时抛出异常
             * offer()：非阻塞插入，队列满时，返回false
             * put（）：阻塞插入
             */
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
            /**
             * remove（）:删除队首，队列为空，抛出异常
             * poll()：非阻塞删除
             * take()：阻塞删除
             */
            queue.take();
            System.out.println("【消费者】处理产品成功;此时仓库库存为->"+queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
