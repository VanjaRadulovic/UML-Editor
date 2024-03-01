package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.observer.IPublisher;

public interface MessageGenerator extends IPublisher {

    void generateMessage(Enum e);
}
