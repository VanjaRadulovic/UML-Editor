package raf.dsw.classycraft.app.repository.implementation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class DiagramElement extends Node{
    public DiagramElement(String name, Node parent, String type) {
        super(name, parent, type);
    }
}
