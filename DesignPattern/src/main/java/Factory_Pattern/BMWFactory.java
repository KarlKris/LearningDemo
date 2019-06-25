package Factory_Pattern;

import Factory_Pattern.Entity.BMW;
import Factory_Pattern.Entity.Car;

public class BMWFactory implements CarFactory{

    public Car getCar() {
        return new BMW();
    }
}
