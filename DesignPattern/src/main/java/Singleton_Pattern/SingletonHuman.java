package Singleton_Pattern;

import java.util.UUID;

public class SingletonHuman {

    private String No;

    public String getNo() {
        return No;
    }

    private SingletonHuman() {
        this.No = UUID.randomUUID().toString();
        System.out.println(No);
    }

    public static SingletonHuman getInstance(){
        return SingletonHumanHolder.singletonHuman;
    }

    static class SingletonHumanHolder {
        private final static SingletonHuman singletonHuman = new SingletonHuman();
    }
}
