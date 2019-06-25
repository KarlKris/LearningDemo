package com.Decorator_Pattern;

import Decorator_Pattern.*;


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
