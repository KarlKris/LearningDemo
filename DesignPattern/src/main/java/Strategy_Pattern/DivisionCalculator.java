package Strategy_Pattern;

public class DivisionCalculator implements Calculator{
    @Override
    public void calculate(int a, int b) {
        int  divisor =(int)( Math.log(b)/Math.log(2.0) );//返回b是2的几次幂
        int ans = a >> divisor ;//移位符号，除法
        System.out.println(a+"/"+b+" = "+ans);
    }
}
