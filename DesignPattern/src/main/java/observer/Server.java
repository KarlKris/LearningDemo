package observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server implements Subject{

    private final int LIST_SIZE = 10;
    private List<Observer> list = new ArrayList<>(LIST_SIZE);

    @Override
    public void register(Observer o) {
        list.add(o);
    }

    @Override
    public void remove(Observer o) {
        if (!list.isEmpty()){
            if (list.contains(o)){
                list.remove(o);
            }
        }
    }


    @Override
    public void notifyObserver() {
        Iterator<Observer> it = list.iterator();
        while (it.hasNext()){
            it.next().update(process());
        }
    }

    private String process(){
        return "holle";
    }
}
