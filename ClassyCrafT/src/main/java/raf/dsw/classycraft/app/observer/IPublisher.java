package raf.dsw.classycraft.app.observer;

public interface IPublisher {
    void addSubscriber(Object obj);
    void removeSubscriber(Object obj);
    void notifySubscribers(Object obj,Enum e);
}