package pcpattern.entity;

import pcpattern.AbstractStorage;

public class Customer implements Runnable {

    private AbstractStorage storage;
    private int num;

    public Customer(AbstractStorage storage, int num) {
        this.storage = storage;
        this.num = num;
    }

    @Override
    public void run() {
        comsume(num);
    }

    public void comsume(int num) {
        try {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                storage.remove();
            }
            System.out.println("【消费者】" + Thread.currentThread().getName() + "是否被中断：" + Thread.currentThread().isInterrupted());
        } catch (InterruptedException e) {
            System.out.println("【消费者】" + Thread.currentThread().getName() + "线程中断");
            e.printStackTrace();
        }
    }
}
