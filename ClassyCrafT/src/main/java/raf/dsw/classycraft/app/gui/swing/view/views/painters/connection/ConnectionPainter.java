package raf.dsw.classycraft.app.gui.swing.view.views.painters.connection;

import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.ClassPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.InterfacePainter;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.Connection;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

public class ConnectionPainter extends ElementPainter {
    public ConnectionPainter(DiagramElement element) {
        super(element);
    }

    @Override
    public void paint(Graphics g) {
        Shape shape = createShape();
        shape = transform.createTransformedShape(shape);
        super.shape = shape;
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(shape);
    }

    public Shape createShape(){
        GeneralPath path = new GeneralPath();

        Point startPoint = ((Connection)element).getStart();
        Point endPoint = ((Connection)element).getEnd();

        if (startPoint != null && endPoint != null) {
            Point midPoint = new Point(startPoint.x, endPoint.y);

            path.moveTo(startPoint.x, startPoint.y);
            path.lineTo(midPoint.x, midPoint.y);
            path.lineTo(endPoint.x, endPoint.y);
        }

        return path;
    }
}
