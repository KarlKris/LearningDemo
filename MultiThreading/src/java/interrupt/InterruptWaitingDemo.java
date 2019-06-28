package interrupt;

public class InterruptWaitingDemo extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // 模拟任务代码
                Thread.sleep(2000);
                System.out.println("======================");
            } catch (InterruptedException e) {
                // ... 清理操作
                System.out.println(isInterrupted()); //false
                //当抛出中断异常时，中断标志位会被清空，即由true变为false
                //因为线程为了处理异常已经重新处于就绪状态。
                // 重设中断标志位
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(isInterrupted());
    }

    public static void main(String[] args) {
        InterruptWaitingDemo thread = new InterruptWaitingDemo();
        thread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        thread.interrupt();
    }
}
