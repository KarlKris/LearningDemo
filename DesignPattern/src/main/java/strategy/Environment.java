package strategy;

public class Environment {

    private Calculator calculator;

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    public void calculator(int a,int b){
        calculator.calculate(a,b);
    }
}
