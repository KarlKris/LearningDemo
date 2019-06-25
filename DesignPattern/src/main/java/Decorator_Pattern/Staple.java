package Decorator_Pattern;

/**
 * 主食
 */
public abstract class Staple implements Food{


    @Override
    public abstract String getName();

    @Override
    public abstract double getPrice();
}
