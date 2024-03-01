package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.gui.swing.tree.model.TreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.TreeView;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.ProjectExplorer;

public interface Tree {

    TreeView generateTree(ProjectExplorer projectExplorer);
    void addChild(TreeItem child);

    void removeChild(Node node);

    void rename();

    TreeItem getSelectedNode();
    void setSelectedNode(Node node);

}
