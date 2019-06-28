package pcpattern;

public interface AbstractStorage {

    void add(Object o)throws InterruptedException;
    void remove() throws InterruptedException;
}
