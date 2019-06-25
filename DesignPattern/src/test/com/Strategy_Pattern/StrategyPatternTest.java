package com.Strategy_Pattern;

import Strategy_Pattern.*;

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
