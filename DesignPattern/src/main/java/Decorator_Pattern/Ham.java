package Decorator_Pattern;

public class Ham extends Jardiniere{
    private String name;
    private double price;
    private Jardiniere jardiniere;

    public Ham() {
        this.name = "火腿";
        this.price = 2d;
    }

    public Ham(Jardiniere jardiniere) {
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
