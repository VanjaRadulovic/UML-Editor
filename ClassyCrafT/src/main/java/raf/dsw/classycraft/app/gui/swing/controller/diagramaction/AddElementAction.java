package raf.dsw.classycraft.app.gui.swing.controller.diagramaction;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractRudokAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddElementAction extends AbstractRudokAction{


    public AddElementAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/plus.png"));
        putValue(NAME, "New Element");
        putValue(SHORT_DESCRIPTION, "New Element");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getDesktop().changeToAddElementState();
    }
}
