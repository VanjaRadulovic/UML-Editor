package raf.dsw.classycraft.app.logger;

import raf.dsw.classycraft.app.error.messageGenerator.Message;
import raf.dsw.classycraft.app.observer.ISubscriber;

public interface Logger extends ISubscriber {
    public void log(Message message);
}
