package raf.dsw.classycraft.app.repository.implementation.elements.connection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;

import java.awt.*;
@Getter
@Setter
@NoArgsConstructor
public class Generalization extends Connection{
    public Generalization(String name, Node parent, Interclass from, Interclass to, Point start, Point end) {
        super(name, parent, from, to, start, end, "generalization");
    }
}
