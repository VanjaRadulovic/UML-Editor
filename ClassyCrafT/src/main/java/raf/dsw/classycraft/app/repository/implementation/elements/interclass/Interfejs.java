package raf.dsw.classycraft.app.repository.implementation.elements.interclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Method;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class Interfejs extends Interclass{


    public List<Method> methods;
    public Interfejs(String name, Node parent, String visability, Point position, int size) {
        super(name, parent, visability, position, size, "interfejs");
        methods = new ArrayList<>();
    }

    public void addMethod(Method m){
        this.methods.add(m);
    }
}
