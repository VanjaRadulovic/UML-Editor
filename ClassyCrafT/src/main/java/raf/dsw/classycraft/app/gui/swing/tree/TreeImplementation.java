package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.TreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.TreeView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.repository.ActionType;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.Enumeration;

public class TreeImplementation implements Tree, ISubscriber {

    private TreeView treeView;
    private DefaultTreeModel treeModel;

    @Override
    public TreeView generateTree(ProjectExplorer projectExplorer) {
        TreeItem root = new TreeItem(projectExplorer);
        treeModel = new DefaultTreeModel(root);
        treeView = new TreeView(treeModel);
        ((IPublisher) ApplicationFramework.getInstance().getRepository()).addSubscriber(this);
        return treeView;
    }

    @Override
    public void addChild(TreeItem child) {
        setSelectedNode(child.getNode().getParent());
        TreeItem parent = MainFrame.getInstance().getTree().getSelectedNode();

        parent.add(child);

        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void removeChild(Node node) {
        TreeItem child = MainFrame.getInstance().getTree().getSelectedNode();
        if (child == null) return;
        treeModel.removeNodeFromParent(child);
    }

    @Override
    public void rename() {
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public TreeItem getSelectedNode() {
        return (TreeItem) treeView.getLastSelectedPathComponent();
    }

    @Override
    public void setSelectedNode(Node node){
        TreeItem root = (TreeItem) treeModel.getRoot();
        TreeItem item = findItem(root, node);
        treeView.setSelectionPath(new TreePath(item.getPath()));
    }

    private TreeItem findItem(TreeItem root, Node node){
        if (root == null) {
            return null;
        }

        if (root.getNode().equals(node)) {
            return root;
        }

        if (root.getChildCount() >= 0) {
            Enumeration<TreeNode> en = root.children();
            while(en.hasMoreElements()){
                TreeItem item = (TreeItem) en.nextElement();
                TreeItem result = findItem(item, node);
                if (result != null){
                    return result;
                }
            }
        }

        return null;
    }

    @Override
    public void update(Object obj, Enum e) {
        if(obj instanceof Node) {
            if (e.equals(ActionType.ADD)) {
                addChild(new TreeItem((Node) obj));
            }
            if (e.equals(ActionType.DELETE)) {
                removeChild((Node) obj);
            }
            if (e.equals(ActionType.RENAME)) {
                rename();
            }
        }
    }
}
