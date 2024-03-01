package raf.dsw.classycraft.app.repository;

import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;
import raf.dsw.classycraft.app.repository.implementation.*;
import raf.dsw.classycraft.app.repository.implementation.Package;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.*;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Enumeracija;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;

import java.awt.*;

public class NodeFactory {

    private static NodeFactory instance;

    public static NodeFactory getInstance() {
        if(instance == null)
            return new NodeFactory();
        return instance;
    }
    public NodeComposite createNode(NodeComposite parent, String name, String author ,String path, boolean pack){

        if(parent instanceof ProjectExplorer){
            return new Project(name, parent, author, path);
        }
        if(parent instanceof Project){
            return new Package(name, parent);
        }
        if(parent instanceof Package && pack){
            return new Package(name, parent);
        }
        if(parent instanceof Package && !pack){
            return new Diagram(name, parent);
        }
        return null;
    }

    public Node createInterClass(NodeComposite parent, String name, String visibility, Point position, int size, ElementType elementType) {
        if(parent instanceof Diagram && elementType.equals(ElementType.CLASS)){
            return new Klasa(name, parent,visibility,position,size);
        }
        if(parent instanceof Diagram && elementType.equals(ElementType.INTERFACE)){
            return new Interfejs(name, parent,visibility,position,size);
        }
        if(parent instanceof Diagram && elementType.equals(ElementType.ENUM)){
            return new Enumeracija(name, parent,visibility,position,size);
        }

        return null;
    }

    public Node createConnection(NodeComposite parent, String name, Interclass from, Interclass to, Point start, Point end, ElementType elementType) {
        if(parent instanceof Diagram && elementType.equals(ElementType.CONNECTION)){
            return new Connection(name, parent, from,to, start, end, "connection");
        }
        if(parent instanceof Diagram && elementType.equals(ElementType.AGGREGATION)){
            return new Aggregation(name, parent, from,to, start, end);
        }
        if(parent instanceof Diagram && elementType.equals(ElementType.COMPOSITION)){
            return new Composition(name, parent, from,to, start, end);
        }
        if(parent instanceof Diagram && elementType.equals(ElementType.DEPENDENCE)){
            return new Dependence(name, parent, from,to, start, end);
        }
        if(parent instanceof Diagram && elementType.equals(ElementType.GENERALIZATION)){
            return new Generalization(name, parent, from,to, start, end);
        }
        return null;
    }


}
