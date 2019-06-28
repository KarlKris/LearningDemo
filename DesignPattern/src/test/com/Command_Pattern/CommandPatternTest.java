package com.Command_Pattern;

import command.*;

/**
 * 命令模式：将一个请求封装成对象，针对每一个不同的请求对象，指定对应的处理方法
 * 对于invoker，也就是命令发布者，只需要制定命令并发布出去就可以了
 * 而Command，命令，其本身要绑定对于的执行者。
 * receiver，也就是命令执行者，执行命令即可
 * 实现了Command和Handler的分离。
 * 命令模式则是通过不同的命令做不同的事情，常含有（关联）接收者。
 */
public class CommandPatternTest {

    public static void main(String[] args) {
        Handler onHandler = new TurnOnHandler();
        Handler offHandler = new TurnOffHandler();

        Command turnOn = new TurnOnCommand(onHandler);
        Command turnOff = new TurnOffCommand(offHandler);

        Invoker invoker = new Invoker();
        invoker.setCommand(turnOn);
        invoker.executeCommand();
        System.out.println("----------------------------------");
        invoker.setCommand(turnOff);
        invoker.executeCommand();
    }
}
