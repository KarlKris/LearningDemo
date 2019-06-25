package Factory_Pattern.Entity;

public class BMW  extends Car{

    private String NAME = "BMW";
    private Double SPEED = 110d;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Double getSpeed() {
        return SPEED;
    }
}
