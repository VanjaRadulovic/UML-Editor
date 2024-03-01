package raf.dsw.classycraft.app.gui.swing;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.core.Gui;
import raf.dsw.classycraft.app.error.messageGenerator.Message;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;

public class SwingGui implements Gui, ISubscriber {

    private MainFrame instance;

    public SwingGui() {
    }

    @Override
    public void start() {
        instance = MainFrame.getInstance();
        instance.setVisible(true);
        ((IPublisher) ApplicationFramework.getInstance().getMessageGenerator()).addSubscriber(this);
    }


    @Override
    public void update(Object obj, Enum e) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),((Message)obj).getMessage(),((Message)obj).getErrorType().toString().replace('_',' '),JOptionPane.ERROR_MESSAGE);
    }
}
