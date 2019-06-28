package concurrent.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanDemo {

    private AtomicBoolean atomicBoolean;
    private volatile boolean flag;

    public AtomicBooleanDemo() {
        atomicBoolean = new AtomicBoolean(false);
        flag = false;
    }




}
