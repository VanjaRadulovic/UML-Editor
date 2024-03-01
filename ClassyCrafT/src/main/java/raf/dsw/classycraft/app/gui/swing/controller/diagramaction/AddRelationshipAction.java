package raf.dsw.classycraft.app.gui.swing.controller.diagramaction;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractRudokAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.repository.ElementType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddRelationshipAction extends AbstractRudokAction {


    public AddRelationshipAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/right-arrow.png"));
        putValue(NAME, "Add relationship");
        putValue(SHORT_DESCRIPTION, "Add relationship");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         MainFrame.getInstance().getDesktop().changeToAddRelationshipState();
    }
}
