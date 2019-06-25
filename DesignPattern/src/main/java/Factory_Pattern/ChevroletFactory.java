package Factory_Pattern;

import Factory_Pattern.Entity.Car;
import Factory_Pattern.Entity.Chevrolet;

public class ChevroletFactory implements CarFactory{

    public Car getCar() {
        return new Chevrolet();
    }
}
