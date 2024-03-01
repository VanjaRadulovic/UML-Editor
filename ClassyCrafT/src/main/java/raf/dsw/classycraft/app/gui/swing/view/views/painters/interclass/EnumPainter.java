package raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass;

import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Attribute;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Enumeracija;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class EnumPainter extends InterclassPainter{
    public EnumPainter(DiagramElement element) {
        super(element);
    }

    @Override
    public void paint(Graphics g) {
        Shape shape = createClassShape(g);
        shape = transform.createTransformedShape(shape);
        super.shape = shape;
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(shape);
        drawString(g2d, g2d.getFont(), shape.getBounds2D(), element.getName());
        drawAttributes(g2d, g2d.getFont(), shape.getBounds2D());
    }

    private void drawAttributes(Graphics2D g, Font font, Rectangle2D r) {
        List<Attribute> attributes = ((Enumeracija) element).getAttributes();
        FontMetrics fm = g.getFontMetrics();

        int lineHeight = fm.getHeight();
        int padding = 5;
        int k = lineHeight + 2 * padding;

        for (Attribute attribute : attributes){
            FontRenderContext frc = new FontRenderContext(null, true, true);

            Rectangle2D r2D = font.getStringBounds(attribute.getName(), frc);
            int rHeight = (int) Math.round(r2D.getHeight());

            int x = (int) r.getX() + 5;
            int y = (int) r.getY() + rHeight + k; // Adjusted for baseline, assuming positive y is down

            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString(attribute.getName(), x, y);
            k = k + lineHeight;
        }
    }


    private Shape createClassShape(Graphics g) {
        FontMetrics fm = g.getFontMetrics();

        int lineHeight = fm.getHeight();
        int padding = 5;
        int headerHeight = lineHeight + 2 * padding;

        int attributeHeight = 10;

        if (((Enumeracija) element).getAttributes() != null && !((Enumeracija) element).getAttributes().isEmpty())
            attributeHeight = (((Enumeracija) element).getAttributes().size() * lineHeight) + (((Enumeracija) element).getAttributes().size() - 1) * padding;

        int totalHeight = headerHeight + attributeHeight + padding;

        GeneralPath classShape = getGeneralPath(totalHeight, headerHeight);

        return classShape;
    }

    private GeneralPath getGeneralPath(int totalHeight, int headerHeight) {
        double x = ((Enumeracija) element).getPosition().getX();
        double y = ((Enumeracija) element).getPosition().getY();
        double width = ((Enumeracija) element).getSize();
        Rectangle2D classRectangle = new Rectangle2D.Double(x, y, width, totalHeight);

        Line2D separator1 = new Line2D.Double(x, y + headerHeight, x + width, y + headerHeight);

        GeneralPath classShape = new GeneralPath();
        classShape.append(classRectangle, false);
        classShape.append(separator1, false);
        return classShape;
    }
}
