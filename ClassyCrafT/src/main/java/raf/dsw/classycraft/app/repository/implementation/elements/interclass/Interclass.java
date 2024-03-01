package raf.dsw.classycraft.app.repository.implementation.elements.interclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;

import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
public abstract class Interclass extends DiagramElement {

    private String visibility;
    @JsonIgnore
    private Point position;
    @JsonIgnore
    private int size;

    public Interclass(String name, Node parent, String visibility, Point position, int size, String type) {
        super(name, parent, type);
        this.visibility = visibility;
        this.position = position;
        this.size = size;
    }
}
