package strategy;

public class MultiplicationCalculator implements Calculator{
    @Override
    public void calculate(int a, int b) {
        int  multiplier =(int)( Math.log(b)/Math.log(2.0) );//返回b是2的几次幂
        int ans = a << multiplier;
        System.out.println(a+"*"+b+" = "+ans);
    }
}
