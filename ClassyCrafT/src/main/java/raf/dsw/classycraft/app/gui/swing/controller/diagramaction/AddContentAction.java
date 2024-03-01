package raf.dsw.classycraft.app.gui.swing.controller.diagramaction;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractRudokAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class AddContentAction extends AbstractRudokAction {


    public AddContentAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(NAME, "Add content");
        putValue(SHORT_DESCRIPTION, "Add content");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getDesktop().changeToAddContentState();
    }

}

