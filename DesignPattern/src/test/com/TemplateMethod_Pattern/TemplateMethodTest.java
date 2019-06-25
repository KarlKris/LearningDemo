package com.TemplateMethod_Pattern;

import TemplateMethod_Pattern.Cleaner;
import TemplateMethod_Pattern.Programmer;
import TemplateMethod_Pattern.Worker;

public class TemplateMethodTest {

    public static void main(String[] args) {
        Worker cleaner = new Cleaner();
        cleaner.work();
        Worker programmer = new Programmer();
        programmer.work();
    }
}
