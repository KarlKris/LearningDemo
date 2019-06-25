package Observer_Pattern;

public interface Subject {

    void register(Observer o);
    void remove(Observer o);
    void notifyObserver();
}
