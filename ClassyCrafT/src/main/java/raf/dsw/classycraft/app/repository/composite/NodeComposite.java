package raf.dsw.classycraft.app.repository.composite;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.observer.IPublisher;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class NodeComposite extends Node{

    public List<Node> children;

    public NodeComposite(String name, Node parent, String type) {
        super(name, parent, type);
        this.children = new ArrayList<>();
    }

    public NodeComposite(String name, Node parent, String type, List<Node> children) {
        super(name, parent, type);
        this.children = children;
    }

    public abstract void removeChildren(Node child);
    public abstract void addChild(Node child);

    public Node getChildByName(String name) {
        for (Node child: this.getChildren()) {
            if (name.equals(child.getName())) {
                return child;
            }
        }
        return null;
    }

}
