package raf.dsw.classycraft.app.gui.swing.tree.view;

import raf.dsw.classycraft.app.gui.swing.tree.controller.TreeCellEditor;
import raf.dsw.classycraft.app.gui.swing.tree.controller.TreeSelectionListener;
import raf.dsw.classycraft.app.gui.swing.tree.model.TreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.MyTabbedPane;
import raf.dsw.classycraft.app.repository.implementation.Package;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TreeView extends JTree {


    public TreeView(DefaultTreeModel defaultTreeModel) {
        setModel(defaultTreeModel);
        TreeCellRenderer ruTreeCellRenderer = new TreeCellRenderer();
        addTreeSelectionListener(new TreeSelectionListener());
        setCellEditor(new TreeCellEditor(this, ruTreeCellRenderer));
        setCellRenderer(ruTreeCellRenderer);
        setEditable(true);

        initializeMouseListener();
    }

    private void initializeMouseListener(){
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int x = e.getX();
                    int y = e.getY();

                    int row = getRowForLocation(x, y);

                    if (row != -1) {
                        TreePath path = getPathForRow(row);

                        if(((TreeItem)path.getLastPathComponent()).getNode() instanceof Package){
                            for(Component c : MainFrame.getInstance().getDesktop().getComponents()){
                                if(c instanceof MyTabbedPane){
                                    ((MyTabbedPane) c).openPackage((TreeItem)path.getLastPathComponent());
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
