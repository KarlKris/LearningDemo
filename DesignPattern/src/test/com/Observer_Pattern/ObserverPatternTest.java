package com.Observer_Pattern;

import observer.Customer;
import observer.Observer;
import observer.Server;
import observer.Subject;

/**
 * 观察者模式：发布-订阅模型（一对多），总体而言是利用容器和回调实现的
 *            当一个对象的状态改变时，所有依赖它的对象均会得到通知，功能类似监听器
 */
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
