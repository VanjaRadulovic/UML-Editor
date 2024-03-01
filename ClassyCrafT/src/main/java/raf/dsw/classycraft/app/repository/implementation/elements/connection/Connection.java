package raf.dsw.classycraft.app.repository.implementation.elements.connection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.repository.ActionType;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;

import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
public class Connection extends DiagramElement {
    private Interclass from;
    private Interclass to;
    @JsonIgnore
    private Point start;
    @JsonIgnore
    private Point end;

    public Connection(String name, Node parent,Interclass from,Interclass to, Point start, Point end, String type) {
        super(name, parent, type);
        this.from = from;
        this.to = to;
        this.start = start;
        this.end = end;
    }

    public void changeEnd(Point point){
        this.end = point;
        getParent().notifySubscribers(this, ActionType.MODIFYCONNECTION);
    }

    public void changeStart(Point point){
        this.start = point;
    }
}
