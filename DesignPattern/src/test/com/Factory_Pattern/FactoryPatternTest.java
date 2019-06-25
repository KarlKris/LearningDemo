package com.Factory_Pattern;

import Factory_Pattern.Entity.Car;
import Factory_Pattern.BenzFactory;
import Factory_Pattern.CarFactory;

public class FactoryPatternTest {

    public static void main(String[] args) {
        CarFactory factory = new BenzFactory();
        Car car = factory.getCar();
        System.out.println("汽车品牌："+car.getName()
                            +",速度："+car.getSpeed());
    }
}
