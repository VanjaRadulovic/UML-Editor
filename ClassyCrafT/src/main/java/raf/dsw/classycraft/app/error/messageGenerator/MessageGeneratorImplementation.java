package raf.dsw.classycraft.app.error.messageGenerator;

import raf.dsw.classycraft.app.core.MessageGenerator;
import raf.dsw.classycraft.app.error.ErrorType;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MessageGeneratorImplementation implements MessageGenerator {

    private List<ISubscriber> subscribers;
    private Message message;

    public MessageGeneratorImplementation() {
        subscribers = new ArrayList<>();
    }

    @Override
    public void generateMessage(Enum e) {

        if(e.equals(ErrorType.NAME_EXISTS)){
            message = new Message("Data komponenta sa istim imenom vec postoji", (ErrorType) e);
            notifySubscribers(message,e);
        }
        if(e.equals(ErrorType.PROJECT_EXPLORER_CANNOT_BE_DELETED)){
            message = new Message("Nije moguce izbrisati selektovan cvor",(ErrorType) e);
            notifySubscribers(message,e);
        }
        if(e.equals(ErrorType.NO_NODE_SELECTED)){
            message = new Message("Morate odabrati cvor za zeljenu akciju",(ErrorType) e);
            notifySubscribers(message,e);
        }
        if(e.equals(ErrorType.NAME_CANNOT_BE_EMPTY)){
            message = new Message("Polje ne moze biti prazno",(ErrorType) e);
            notifySubscribers(message,e);
        }
        if(e.equals(ErrorType.MUST_SELECT_PROJECT)){
            message = new Message("Morate odabrati projekat",(ErrorType) e);
            notifySubscribers(message,e);
        }
        if(e.equals(ErrorType.CANNOT_ADD_TO_DIAGRAM)){
            message = new Message("Na diagram se ne moze dodavati",(ErrorType) e);
            notifySubscribers(message,e);
        }
    }

    @Override
    public void addSubscriber(Object obj) {
        if(obj != null && !subscribers.contains(obj) && obj instanceof ISubscriber){
            subscribers.add((ISubscriber) obj);
        }
    }

    @Override
    public void removeSubscriber(Object obj) {
        if(obj != null && subscribers.contains(obj)){
            subscribers.remove(obj);
        }
    }

    @Override
    public void notifySubscribers(Object obj, Enum e) {
        for (ISubscriber s: subscribers){
            s.update(obj,e);
        }
    }
}