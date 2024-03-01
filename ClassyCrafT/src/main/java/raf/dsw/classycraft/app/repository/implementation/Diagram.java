package raf.dsw.classycraft.app.repository.implementation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.repository.ActionType;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.Connection;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Diagram extends NodeComposite{
    List<ISubscriber> subscribers;

    public Diagram(String name, Node parent) {
        super(name, parent, "diagram");
        this.subscribers = new ArrayList<>();
    }

    @Override
    public void removeChildren(Node child) {
        if (child instanceof Interclass && this.getChildren().contains(child) || child instanceof Connection && this.getChildren().contains((Connection)child)){
            this.getChildren().remove(child);
            this.notifySubscribers(child, ActionType.DELETEELEMENT);
        }
    }

    @Override
    public void addChild(Node child) {
        if (child != null){
            if(child instanceof DiagramElement) {
                DiagramElement diagramElement = (DiagramElement) child;
                if (!this.getChildren().contains(diagramElement)) {
                    this.getChildren().add(diagramElement);
                    this.notifySubscribers(child, ActionType.ADDELEMENT);
                }
            }
        }
    }
}
