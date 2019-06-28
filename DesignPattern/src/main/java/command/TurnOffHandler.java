package command;

public class TurnOffHandler implements Handler{
    @Override
    public void action() {
        System.out.println("关闭风扇");
    }
}
