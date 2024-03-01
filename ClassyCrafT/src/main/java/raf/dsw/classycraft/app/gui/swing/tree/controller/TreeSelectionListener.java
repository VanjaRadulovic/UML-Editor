package raf.dsw.classycraft.app.gui.swing.tree.controller;

import raf.dsw.classycraft.app.gui.swing.tree.model.TreeItem;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

public class TreeSelectionListener implements javax.swing.event.TreeSelectionListener {

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        TreeItem treeItemSelected = (TreeItem)path.getLastPathComponent();
        //System.out.println("Selektovan cvor:"+ treeItemSelected.getNode().getName());
        //System.out.println("getPath: "+e.getPath());
    }
}


