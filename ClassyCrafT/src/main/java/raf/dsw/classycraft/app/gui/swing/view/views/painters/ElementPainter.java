package raf.dsw.classycraft.app.gui.swing.view.views.painters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public abstract class ElementPainter {
    protected Shape shape;
    public DiagramElement element;
    public AffineTransform transform;
    public abstract void paint(Graphics g);

    public ElementPainter(DiagramElement element) {
        this.element = element;
    }

    @Override
    public String toString(){
        return element.getName();
    }
}
