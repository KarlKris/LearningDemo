package com.pcpattern;

import pcpattern.AbstractStorage;
import pcpattern.blockingQueue.ArrayBlockingQueueStorage;
import pcpattern.blockingQueue.LinkedBlockingQueueStorage;
import pcpattern.entity.Customer;
import pcpattern.entity.Productor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BasedBlockingQueueTest {

    public static void main(String[] args) {
        AbstractStorage storage = new LinkedBlockingQueueStorage();

        ExecutorService product = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorService customer = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i=0;i<10;i++){
            customer.submit(new Customer(storage,10));
            product.submit(new Productor(storage,10));
        }
    }
}
