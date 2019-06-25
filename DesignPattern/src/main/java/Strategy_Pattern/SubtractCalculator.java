package Strategy_Pattern;

public class SubtractCalculator implements Calculator{
    @Override
    public void calculate(int a, int b) {
        int ans = a-b;
        System.out.println( a+"-"+b+" = "+ans);
    }
}
