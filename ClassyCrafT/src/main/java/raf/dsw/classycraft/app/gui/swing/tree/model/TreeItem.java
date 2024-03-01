package raf.dsw.classycraft.app.gui.swing.tree.model;

import raf.dsw.classycraft.app.repository.composite.Node;
import lombok.Getter;
import lombok.Setter;

import javax.swing.tree.DefaultMutableTreeNode;

@Getter
@Setter
public class TreeItem extends DefaultMutableTreeNode {

    private Node node;

    public TreeItem(Node nodeModel) {
        this.node = nodeModel;
    }

    @Override
    public String toString() {
        return node.getName();
    }

    public void setName(String name) {
        this.node.changeName(name);
    }
}
