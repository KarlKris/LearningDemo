package Decorator_Pattern;

public class Noodles extends Staple {

    private String name ;
    private double price;
    private Food jardiniere;

    public Noodles() { }

    public Noodles(Food jardiniere) {
        this.name = "汤面";
        this.price = 10d;
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
