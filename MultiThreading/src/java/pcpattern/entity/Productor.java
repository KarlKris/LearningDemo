package pcpattern.entity;

import pcpattern.AbstractStorage;

public class Productor implements Runnable{

    private AbstractStorage storage;
    private int num;

    public Productor(AbstractStorage storage,int num) {
        this.storage = storage;
        this.num = num;
    }

    @Override
    public void run() {
        produce();
    }

    public void produce(){
        try{
            while (!Thread.currentThread().isInterrupted()){
                storage.add(new Object());
            }
            System.out.println("【生产者】"+Thread.currentThread().getName()+"是否被中断："+Thread.currentThread().isInterrupted());
        }catch (InterruptedException e){
            System.out.println("【生产者】"+Thread.currentThread().getName()+"发生中断");
            e.printStackTrace();
        }

    }
}
