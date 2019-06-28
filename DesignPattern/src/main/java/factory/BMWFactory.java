package factory;

import factory.Entity.BMW;
import factory.Entity.Car;

public class BMWFactory implements CarFactory{

    public Car getCar() {
        return new BMW();
    }
}
