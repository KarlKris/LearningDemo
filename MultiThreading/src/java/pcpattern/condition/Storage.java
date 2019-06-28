package pcpattern.condition;

import pcpattern.AbstractStorage;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage implements AbstractStorage {

    private final int SIZE = 100;
    private List list;
    private Lock lock;
    private Condition condition;

    public Storage() {
        list = new LinkedList();
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    @Override
    public void add(Object o){
        lock.lock();
        int size = SIZE - list.size();
        System.out.println("【生产者】仓库剩余存量为->"+size);
        try{
            while(size<=0){
                System.out.println("【生产者】仓库已满，等待消费者处理");
                condition.await();
                size = SIZE - list.size();
            }
            list.add(o);
            System.out.println("【生产者】生产一个产品；此时仓库库存为->"+list.size());
            condition.signalAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void remove(){
        lock.lock();
        int size = list.size();
        System.out.println("【消费者】仓库库存为->"+size);
        try{
            while (size<=0){
                System.out.println("【消费者】仓库库存为0，等待生产者生产产品");
                condition.await();
                size = list.size();
            }
            list.remove(0);
            System.out.println("【消费者】处理一个产品，此时仓库库存为->"+list.size());
            condition.signalAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
