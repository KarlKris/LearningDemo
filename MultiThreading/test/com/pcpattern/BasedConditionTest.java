package com.pcpattern;

import pcpattern.AbstractStorage;
import pcpattern.condition.Storage;
import pcpattern.entity.Customer;
import pcpattern.entity.Productor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BasedConditionTest {

    public static void main(String[] args) {
        AbstractStorage storage = new Storage();

        ExecutorService product = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorService customer = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i=0;i<10;i++){
            customer.submit(new Customer(storage,10));
            product.submit(new Productor(storage,10));
        }
    }
}
