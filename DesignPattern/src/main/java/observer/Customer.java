package observer;

public class Customer implements Observer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String msg) {
        System.out.println("用户"+name+"收到信息->"+msg);
    }
}
