package Command_Pattern;

public class TurnOnCommand implements Command{

    //命令执行者
    private Handler handler;

    public TurnOnCommand(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.action();
    }

}
