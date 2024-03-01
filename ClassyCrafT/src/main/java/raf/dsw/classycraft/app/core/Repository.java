package raf.dsw.classycraft.app.core;


import raf.dsw.classycraft.app.repository.ElementType;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;
import raf.dsw.classycraft.app.repository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Attribute;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Method;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import java.awt.*;
import java.util.List;

public interface Repository {
    ProjectExplorer getProjectExplorer();
    void addChild(Node node);
    void addChild(NodeComposite parent,String Name, String author, String path, boolean pack);

    void addChild(NodeComposite parent, String name, String visibility, Point position, int size, ElementType elementType);

    void addChild(NodeComposite parent, String name, Interclass from, Interclass to, Point start, Point end, ElementType elementType);

    void rename(Node node, String name);

    void delete(Node child);

    void edit(Node node, String author, String path);

    boolean contains(Node node);

    Node findDiagramByName(Node node, String name);

    void addContent(Node node, List<Attribute> attributeList, List<Method> methodList,String name,String visability);

}
