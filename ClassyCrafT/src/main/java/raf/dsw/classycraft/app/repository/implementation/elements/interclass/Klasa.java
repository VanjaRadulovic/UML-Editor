package raf.dsw.classycraft.app.repository.implementation.elements.interclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Attribute;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Method;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Klasa extends Interclass{

    public List<Method> methods;
    public List<Attribute> attributes;

    public Klasa(String name, Node parent, String visibility, Point position, int size) {
        super(name, parent, visibility, position, size, "klasa");
        attributes = new ArrayList<>();
        methods = new ArrayList<>();
    }

    public void addMethod(Method m){
        this.methods.add(m);
    }

    public void addAttribute(Attribute a){
        this.attributes.add(a);
    }

}
