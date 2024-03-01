package raf.dsw.classycraft.app.repository.implementation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.gui.swing.view.views.PackageView;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Package extends NodeComposite implements IPublisher {
    public Package(String name, Node parent) {
        super(name, parent, "package");
    }

    @Override
    public void addChild(Node child) {
        if (child != null){
            if(child instanceof Diagram){
                Diagram diagram = (Diagram) child;
                if (!this.getChildren().contains(diagram)){
                    this.getChildren().add(diagram);
                    this.notifySubscribers(diagram, null);
                }
            }else if(child instanceof Package){
                Package pack = (Package) child;
                if (!this.getChildren().contains(pack)){
                    this.getChildren().add(pack);
                }
            }
        }
    }

    @Override
    public void removeChildren(Node child) {
        if (child instanceof Diagram && this.getChildren().contains((Diagram)child) || child instanceof Package && this.getChildren().contains((Package)child) ){
            this.getChildren().remove(child);
            notifySubscribers("delete:" + child.getName(), null);
        }

    }
}