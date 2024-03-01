package raf.dsw.classycraft.app.gui.swing.view.bars;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;


public class DiagramToolBar extends JToolBar {

    public DiagramToolBar() {
        super(VERTICAL);
        setFloatable(false);
        add (MainFrame.getInstance().getActionManager().getAddElementAction());
        add (MainFrame.getInstance().getActionManager().getAddRelationship());
        add (MainFrame.getInstance().getActionManager().getAddContent());
        add (MainFrame.getInstance().getActionManager().getDeleteElement());
        add (MainFrame.getInstance().getActionManager().getSelectAction());
        add (MainFrame.getInstance().getActionManager().getZoomInAction());
        add (MainFrame.getInstance().getActionManager().getZoomOutAction());
        add (MainFrame.getInstance().getActionManager().getDuplicateAction());
        add (MainFrame.getInstance().getActionManager().getMoveAction());

    }
}
