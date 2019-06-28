package factory;

import factory.Entity.Benz;
import factory.Entity.Car;

public class BenzFactory implements CarFactory{

    public Car getCar() {
        return new Benz();
    }
}
