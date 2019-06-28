package com.Factory_Pattern;

import factory.Entity.Car;
import factory.BenzFactory;
import factory.CarFactory;

/**
 * 工厂模式：封装对象的创建，不同对象拥有不同的创建工厂。
 *          将对象的创建和使用分离。
 */
public class FactoryPatternTest {

    public static void main(String[] args) {
        CarFactory factory = new BenzFactory();
        Car car = factory.getCar();
        System.out.println("汽车品牌："+car.getName()
                            +",速度："+car.getSpeed());
    }
}
