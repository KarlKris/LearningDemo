package strategy;

public class AddCalculator implements Calculator{

    @Override
    public void calculate(int a, int b) {
        int ans = a+b;
        System.out.println( a+"+"+b+" = "+ans);
    }
}
