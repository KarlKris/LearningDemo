package com.Observer_Pattern;

import Observer_Pattern.Customer;
import Observer_Pattern.Observer;
import Observer_Pattern.Server;
import Observer_Pattern.Subject;

public class ObserverPatternTest {

    public static void main(String[] args) {
        Subject server = new Server();

        Observer user1 = new Customer("小米");
        Observer user2 = new Customer("小白");

        server.register(user1);
        server.register(user2);
        server.notifyObserver();
        System.out.println("-------------------------------------");
        server.remove(user1);
        server.notifyObserver();
    }
}
