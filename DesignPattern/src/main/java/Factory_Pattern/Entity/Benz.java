package Factory_Pattern.Entity;

public class Benz extends Car{

    private String NAME = "BENZ";
    private Double SPEED = 120d;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Double getSpeed() {
        return SPEED;
    }
}
