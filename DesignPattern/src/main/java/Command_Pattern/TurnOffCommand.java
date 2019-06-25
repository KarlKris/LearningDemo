package Command_Pattern;

public class TurnOffCommand implements Command {

    private Handler handler;

    public TurnOffCommand(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.action();
    }
}
