package Factory_Pattern;

import Factory_Pattern.Entity.Benz;
import Factory_Pattern.Entity.Car;

public class BenzFactory implements CarFactory{

    public Car getCar() {
        return new Benz();
    }
}
