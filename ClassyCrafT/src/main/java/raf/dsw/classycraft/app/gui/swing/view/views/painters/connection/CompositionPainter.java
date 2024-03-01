package raf.dsw.classycraft.app.gui.swing.view.views.painters.connection;

import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.Connection;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class CompositionPainter extends ConnectionPainter{
    public CompositionPainter(DiagramElement element) {
        super(element);
    }

    @Override
    public void paint(Graphics g) {
        Shape shape = createShape();
        shape = transform.createTransformedShape(shape);
        super.shape = shape;
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(shape);

        Point startPoint = ((Connection)element).getStart();
        Point endPoint = ((Connection)element).getStart();
        Point midPoint = new Point(startPoint.x, endPoint.y);

        Shape head = getHead(endPoint, midPoint);
        g2d.fill(head);
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

        return path;
    }

    public Shape getHead(Point tip, Point tail){
        int diamondWidth = 10;
        int diamondHeight = 20;

        Polygon diamond = new Polygon();
        diamond.addPoint(0, 0);
        diamond.addPoint(-diamondWidth, diamondHeight / 2);
        diamond.addPoint(0, diamondHeight);
        diamond.addPoint(diamondWidth, diamondHeight / 2);

        AffineTransform tx = new AffineTransform();
        double angle = Math.atan2(tip.y - tail.y, tip.x - tail.x);
        tx.translate(tip.x, tip.y);
        tx.rotate(angle - Math.PI / 2);

        return tx.createTransformedShape(diamond);
    }
}
