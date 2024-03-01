package raf.dsw.classycraft.app.gui.swing.view.bars;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;

public class Toolbar extends JToolBar {

    public Toolbar() {
        super(HORIZONTAL);
        setFloatable(false);

        add (MainFrame.getInstance().getActionManager().getExitAction());
        add (MainFrame.getInstance().getActionManager().getNewAction());
        add (MainFrame.getInstance().getActionManager().getDeleteAction());
        add (MainFrame.getInstance().getActionManager().getEditAction());
        add (MainFrame.getInstance().getActionManager().getOpenAction());
        add (MainFrame.getInstance().getActionManager().getSaveAsAction());
        add (MainFrame.getInstance().getActionManager().getUndoAction());
        add (MainFrame.getInstance().getActionManager().getRedoAction());
    }
}
