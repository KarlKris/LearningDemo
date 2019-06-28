package com.TemplateMethod_Pattern;

import template.Cleaner;
import template.Programmer;
import template.Worker;

/**
 *  模板方法模式：抽象类的具体方法中调用了抽象方法，
 *               该抽象方法由子类自己实现；即具体方法定义了骨架，而抽象方法为其一个细节
 *               即流程已确定，其中方法可以可有不同
 */
public class TemplateMethodTest {

    public static void main(String[] args) {
        Worker cleaner = new Cleaner();
        cleaner.work();
        Worker programmer = new Programmer();
        programmer.work();
    }
}
