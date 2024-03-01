package raf.dsw.classycraft.app.gui.swing.view.views.painters.connection;

import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.Connection;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class DependencePainter extends ConnectionPainter{
    public DependencePainter(DiagramElement element) {
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
        int arrowWidth = 10;
        int arrowHeight = 20;

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
