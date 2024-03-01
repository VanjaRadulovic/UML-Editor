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
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

public class GeneralizationPainter extends ConnectionPainter{
    public GeneralizationPainter(DiagramElement element) {
        super(element);
    }

    @Override
    public void paint(Graphics g) {
        Shape shape = createShape();
        shape = transform.createTransformedShape(shape);
        super.shape = shape;
        Graphics2D g2d = (Graphics2D) g;
        float[] dashPattern = { 4, 4 };
        g2d.setStroke(new BasicStroke(
                1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));
        g2d.draw(shape);
        g2d.setStroke(new BasicStroke());
    }

    @Override
    public Shape createShape(){
        GeneralPath path = new GeneralPath();

        Point startPoint = ((Connection)element).getStart();
        Point endPoint = ((Connection)element).getEnd();
        Point midPoint = new Point(startPoint.x, endPoint.y);


        path.moveTo(startPoint.x, startPoint.y);
        path.lineTo(midPoint.x, midPoint.y);
        path.lineTo(endPoint.x, endPoint.y);


        Shape arrowHeadShape = getHead(endPoint, midPoint);
        path.append(arrowHeadShape, false);
        return path;
    }

    public Shape getHead(Point tip, Point tail){
        // Size and shape of the arrowhead
        int arrowWidth = 5;
        int arrowHeight = 10;

        // Create a polygon for the arrowhead shape
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 0); // Tip of the arrow
        arrowHead.addPoint(-arrowWidth, arrowHeight); // Bottom left
        arrowHead.addPoint(arrowWidth, arrowHeight); // Bottom right

        // Create an AffineTransform for the correct orientation of the arrowhead
        AffineTransform tx = new AffineTransform();
        double angle = Math.atan2(tip.y - tail.y, tip.x - tail.x);
        tx.translate(tip.x, tip.y);
        tx.rotate(angle - Math.PI / 2);

        // Transform the arrowhead shape
        return tx.createTransformedShape(arrowHead);
    }
}
