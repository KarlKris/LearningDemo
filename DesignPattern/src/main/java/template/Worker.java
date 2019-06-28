package template;

public abstract class Worker {

    private String workContent;

    public void work(){
        System.out.println("----------开始工作------------");
        process();
        System.out.println("----------结束工作------------");
    }

    public abstract void process();
}
