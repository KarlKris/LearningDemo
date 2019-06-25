package Factory_Pattern.Entity;

public class Chevrolet extends Car{

    private String NAME = "Chevrolet";
    private Double SPEED = 100d;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Double getSpeed() {
        return SPEED;
    }
}
