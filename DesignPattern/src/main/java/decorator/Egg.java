package decorator;

public class Egg extends Jardiniere{

    private String name;
    private double price;
    private Jardiniere jardiniere;

    public Egg() {
        this.name = "鸡蛋";
        this.price = 1d;
    }

    public Egg(Jardiniere jardiniere) {
        this();
        this.jardiniere = jardiniere;
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        if (jardiniere!=null){
            sb.append(jardiniere.getName());
        }
        sb.append(name);
        return sb.toString();
    }

    @Override
    public double getPrice() {
        if (jardiniere!=null){
            price+=jardiniere.getPrice();
        }
        return price;
    }
}
