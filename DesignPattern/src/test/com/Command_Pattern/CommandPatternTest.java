package com.Command_Pattern;

import Command_Pattern.*;

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
