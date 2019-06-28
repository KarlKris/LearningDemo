package com.Decorator_Pattern;

import decorator.*;

/**
 * 通过继承或实现同一个基类或接口，并子类内部含有基类引用
 * 实现子类的扩展，即，让子类可以拥有更多的特征
 *
 * 实例：Java IO类
 */
public class DecoratorPatternTest {

    public static void main(String[] args) {

        Food fryRice = new FryRice(new Egg(new Ham()));
        Food noodles = new Noodles(new Ham(new Egg()));

        System.out.println(fryRice.getName());
        System.out.println(fryRice.getPrice());
        System.out.println("------------------------------------------");
        System.out.println(noodles.getName());
        System.out.println(noodles.getPrice());
    }
}
