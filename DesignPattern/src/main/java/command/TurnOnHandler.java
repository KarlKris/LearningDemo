package command;

public class TurnOnHandler implements Handler{
    @Override
    public void action() {
        System.out.println("打开风扇吹风");
    }
}
