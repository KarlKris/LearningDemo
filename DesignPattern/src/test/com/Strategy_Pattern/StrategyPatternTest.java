package com.Strategy_Pattern;

import strategy.*;

/**
 * 策略模式：对于同一个接口的不同子类均有自己特有的实现逻辑，
 *          调用方使用时，只需要将需要的子类引用添加进来即可，而且可以替换
 *
 *          策略模式是通过不同的算法做同一件事情
 *
 */
public class StrategyPatternTest {

    public static void main(String[] args) {
        int a = 10 ;int b = 2;
        Calculator add = new AddCalculator();
        Calculator sub = new SubtractCalculator();
        Calculator div = new DivisionCalculator();
        Calculator mul = new MultiplicationCalculator();

        Environment env = new Environment();
        env.setCalculator(add);
        env.calculator(a,b);
        System.out.println("---------------------------------------------");
        env.setCalculator(sub);
        env.calculator(a,b);
        System.out.println("---------------------------------------------");
        env.setCalculator(div);
        env.calculator(a,b);
        System.out.println("---------------------------------------------");
        env.setCalculator(mul);
        env.calculator(a,b);
        System.out.println("---------------------------------------------");
    }
}
