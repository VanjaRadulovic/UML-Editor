package raf.dsw.classycraft.app.repository.implementation.elements.interclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
public class Enumeracija extends Interclass{
    List<Attribute> attributes;

    public Enumeracija(String name, Node parent, String visibility, Point position, int size) {
        super(name, parent, visibility, position, size, "enumeracija");
        attributes = new ArrayList<>();
    }

    public void addAttribute(Attribute a){
        this.attributes.add(a);
    }
}
