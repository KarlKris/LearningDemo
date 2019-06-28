package com.pcpattern;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import pcpattern.AbstractStorage;
import pcpattern.entity.Customer;
import pcpattern.entity.Productor;
import pcpattern.object.Storage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PCPBasedObjectTest {

    public static void main(String[] args) {
        AbstractStorage storage = new Storage();
        ExecutorService product = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorService customer = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i=0;i<2;i++){
            customer.submit(new Customer(storage,5));
            product.submit(new Productor(storage,5));
        }
        customer.shutdownNow();
        product.shutdownNow();
    }
}
