package Decorator_Pattern;

public class FryRice extends Staple{

    private String name ;
    private double price;
    private Food jardiniere;

    public FryRice() { }

    public FryRice(Food jardiniere) {
        this.name = "炒饭";
        this.price = 8d;
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
