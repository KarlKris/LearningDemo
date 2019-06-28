package interrupt;

public class InterruptSynchronizedDemo {

    private static Object lock = new Object();//monitor

    private static class A extends Thread {
        @Override
        public void run() {
            //等待lock锁
            synchronized (lock) {
                //等待标志位被置为true
                while (!Thread.currentThread().isInterrupted()) {
                }
            }
            System.out.println("exit");
        }
    }

    public static void test() throws InterruptedException {
        synchronized (lock) {//获取锁
            A a = new A();
            a.start();
            Thread.sleep(1000);
            //a在等待lock锁，interrupt 无法中断
            a.interrupt();
            //a线程加入当前线程，等待执行完毕
            //此时a线程永远无法执行，因为在synchronized获取锁，
            // 该锁被本方法获取，又调用join方法。所以该方法不会释放锁，因为在等待a线程执行完
            //即synchronized在获取锁的过程中无法响应中断，会一直阻塞，直到获取到锁
            a.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }

}
