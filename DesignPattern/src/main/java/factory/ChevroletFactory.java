package factory;

import factory.Entity.Car;
import factory.Entity.Chevrolet;

public class ChevroletFactory implements CarFactory{

    public Car getCar() {
        return new Chevrolet();
    }
}
