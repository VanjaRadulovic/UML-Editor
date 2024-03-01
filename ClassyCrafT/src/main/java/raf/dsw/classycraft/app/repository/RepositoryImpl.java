package raf.dsw.classycraft.app.repository;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.error.ErrorType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.Project;
import raf.dsw.classycraft.app.repository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.core.Repository;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.Connection;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Attribute;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Method;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Enumeracija;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class RepositoryImpl implements Repository, IPublisher {

    private ProjectExplorer projectExplorer;
    List<ISubscriber> subscribers = new ArrayList<>();
    public RepositoryImpl() {
        projectExplorer = new ProjectExplorer("My Project Explorer");
    }

    @Override
    public ProjectExplorer getProjectExplorer() {
        return projectExplorer;
    }

    @Override
    public void addChild(Node node) {
        ((NodeComposite) node.getParent()).addChild(node);
        this.notifySubscribers(node,ActionType.ADD);
    }

    @Override
    public void addChild(NodeComposite parent, String name,String author, String path, boolean pack) {
        NodeComposite node = NodeFactory.getInstance().createNode(parent, name, author, path, pack);
        parent.addChild(node);
        this.notifySubscribers(node,ActionType.ADD);
    }

    @Override
    public void addChild(NodeComposite parent, String name, String visibility, Point position, int size, ElementType elementType) {
        Node node = NodeFactory.getInstance().createInterClass(parent, name, visibility, position, size, elementType);
        parent.addChild(node);
        this.notifySubscribers(node, ActionType.ADD);
    }

    @Override
    public void addChild(NodeComposite parent, String name, Interclass from, Interclass to, Point start, Point end, ElementType elementType) {
        Node node = NodeFactory.getInstance().createConnection(parent, name, from,to, start, end, elementType);
        parent.addChild(node);
        this.notifySubscribers(node,ActionType.ADD);
    }

    @Override
    public void rename(Node node, String name) {
        if(nameExists(node,name)){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.NAME_EXISTS);
            return;
        }
        if(name.isEmpty()){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.NAME_CANNOT_BE_EMPTY);
            return;
        }
        node.changeName(name);
        this.notifySubscribers(node, ActionType.RENAME);
    }

    @Override
    public void delete(Node child) {
        if(child instanceof ProjectExplorer){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.PROJECT_EXPLORER_CANNOT_BE_DELETED);
            return;
        }
        NodeComposite parent = (NodeComposite) child.getParent();
        parent.removeChildren(child);
        if(child instanceof Interclass){
            List<Node> nodesToRemove = new ArrayList<>();

            for (Node node : parent.getChildren()) {
                if (node instanceof Connection) {
                    Connection connection = (Connection) node;
                    if (connection.getFrom() == child || connection.getTo().equals(child)) {
                        nodesToRemove.add(node);
                    }
                }
            }

            for (Node node : nodesToRemove) {
                parent.removeChildren(node);
                MainFrame.getInstance().getTree().setSelectedNode(node);
                this.notifySubscribers(node, ActionType.DELETE);
            }
            MainFrame.getInstance().getTree().setSelectedNode(child);
        }
        this.notifySubscribers(child, ActionType.DELETE);
    }

    @Override
    public void edit(Node node,String author, String path) {
        ((Project) node).changeAuthor(author);
        ((Project) node).setPath(path);
    }

    private boolean nameExists(Node node,String newName){

        Node parent = node.getParent();

        for (Node n: ((NodeComposite)parent).getChildren()){
            if(n.getName().equals(newName)) return true;

        }
        return false;
    }

    @Override
    public void addSubscriber(Object sub) {
        if(sub != null && !subscribers.contains(sub) && sub instanceof ISubscriber){
            subscribers.add((ISubscriber)sub);
        }
    }

    @Override
    public void removeSubscriber(Object sub) {
        if(sub != null && subscribers.contains(sub)){
            subscribers.remove(sub);
        }
    }

    @Override
    public void notifySubscribers(Object obj, Enum e) {
        for (int i = 0; i < subscribers.size(); i++){
            subscribers.get(i).update(obj,e);
        }
    }


    public boolean contains(Node node){
        Node parent = node.getParent();

        for (Node n: ((NodeComposite)parent).getChildren()){
            if(n.getName().equals(node.getName())) return true;

        }
        return false;
    }

    @Override
    public Node findDiagramByName(Node currentNode, String diagramName) {
        if (currentNode == null) currentNode = projectExplorer;

        if (currentNode instanceof Diagram && currentNode.getName().equals(diagramName)) {
            return currentNode;
        }

        // Iterate over children if the current node is a composite node
        if (currentNode instanceof NodeComposite) {
            for (Node child : ((NodeComposite) currentNode).getChildren()) {
                Node found = findDiagramByName(child, diagramName);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    @Override
    public void addContent(Node node, List<Attribute> attributeList, List<Method> methodList, String name, String visability) {
        if (node instanceof Klasa){
            Klasa klasa = (Klasa) node;
            klasa.getAttributes().clear();
            klasa.getMethods().clear();
            klasa.changeName(name);
            klasa.setVisibility(visability);
            for(Attribute a : attributeList){
                klasa.addAttribute(a);
            }
            for(Method m : methodList){
                klasa.addMethod(m);
            }
            node.getParent().notifySubscribers(node, ActionType.MODIFYELEMENT);
        }else if (node instanceof Interfejs){
            Interfejs interfejs = (Interfejs) node;
            interfejs.getMethods().clear();
            interfejs.changeName(name);
            interfejs.setVisibility(visability);
            for(Method m : methodList){
                    interfejs.addMethod(m);
            }
            node.getParent().notifySubscribers(node, ActionType.MODIFYELEMENT);
        }else if(node instanceof Enumeracija){
            Enumeracija enumeracija = (Enumeracija) node;
            enumeracija.getAttributes().clear();
            enumeracija.changeName(name);
            enumeracija.setVisibility(visability);
            for (Attribute a : attributeList){
                    enumeracija.addAttribute(a);
            }
            node.getParent().notifySubscribers(node, ActionType.MODIFYELEMENT);
        }
    }
}
