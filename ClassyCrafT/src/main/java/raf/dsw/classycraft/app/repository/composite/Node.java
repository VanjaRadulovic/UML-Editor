package raf.dsw.classycraft.app.repository.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.PackageView;
import raf.dsw.classycraft.app.gui.swing.view.views.ProjectView;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.repository.implementation.*;
import raf.dsw.classycraft.app.repository.implementation.Package;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.*;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Enumeracija;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProjectExplorer.class, name = "projectexplorer"),
        @JsonSubTypes.Type(value = Project.class, name = "project"),
        @JsonSubTypes.Type(value = Package.class, name = "package"),
        @JsonSubTypes.Type(value = Diagram.class, name = "diagram"),
        @JsonSubTypes.Type(value = DiagramElement.class, name = "diagramelement"),
        @JsonSubTypes.Type(value = Interclass.class, name = "interclass"),
        @JsonSubTypes.Type(value = Interfejs.class, name = "interfejs"),
        @JsonSubTypes.Type(value = Klasa.class, name = "klasa"),
        @JsonSubTypes.Type(value = Enumeracija.class, name = "enumeracija"),
        @JsonSubTypes.Type(value = Connection.class, name = "connection"),
        @JsonSubTypes.Type(value = Dependence.class, name = "dependence"),
        @JsonSubTypes.Type(value = Generalization.class, name = "generalization"),
        @JsonSubTypes.Type(value = Aggregation.class, name = "aggregation"),
        @JsonSubTypes.Type(value = Composition.class, name = "composition")
})
public abstract class Node implements IPublisher {
    @JsonIgnore
    boolean changed;
    @Setter
    @JsonIgnore
    private List<ISubscriber> subscribers;
    private String name;
    @ToString.Exclude
    @Setter
    @JsonIgnore
    private Node parent;
    private String type;

    public Node(String name, Node parent, String type) {
        this.name = name;
        this.type = type;
        this.parent = parent;
        this.changed = true;
        this.subscribers = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Node) {
            Node otherObj = (Node) obj;
            return this.getName().equals(otherObj.getName());
        }
        return false;
    }

    public void changeName(String name) {
        this.name = name;
        this.notifySubscribers("Name:" + name, null);
    }

    @Override
    public void addSubscriber(Object obj) {
        if(!this.subscribers.contains((ISubscriber) obj)) {
            if(obj instanceof DiagramView){
                Class<?> classToRemove = DiagramView.class;
                List<Object> filteredList = subscribers.stream()
                        .filter(element -> !classToRemove.isInstance(element))
                        .collect(Collectors.toList());
            }else if(obj instanceof PackageView){
                Class<?> classToRemove = PackageView.class;
                List<Object> filteredList = subscribers.stream()
                        .filter(element -> !classToRemove.isInstance(element))
                        .collect(Collectors.toList());

            }else if(obj instanceof ProjectView){
                Class<?> classToRemove = ProjectView.class;
                List<Object> filteredList = subscribers.stream()
                        .filter(element -> !classToRemove.isInstance(element))
                        .collect(Collectors.toList());
            }
            this.subscribers.add((ISubscriber) obj);
        }
    }

    @Override
    public void removeSubscriber(Object obj) {
        this.subscribers.remove((ISubscriber)obj);
    }

    @Override
    public void notifySubscribers(Object obj, Enum e) {
        for(ISubscriber subscriber : this.subscribers){
            subscriber.update(obj, e);
        }
    }

    public void setChanged(boolean val){
        if(val) {
            this.changed = val;
            if (!(this instanceof Project)) {
                Node temp = this.parent;
                while (!(temp instanceof Project)) temp = temp.getParent();
                temp.setChanged(val);
            }
        }else{
            this.changed = false;
        }
    }
}
