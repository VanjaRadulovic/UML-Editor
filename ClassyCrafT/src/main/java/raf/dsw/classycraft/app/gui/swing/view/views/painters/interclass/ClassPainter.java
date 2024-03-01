package raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Attribute;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Method;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.List;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
@Getter
@Setter
public class ClassPainter extends InterclassPainter{

    public ClassPainter(DiagramElement element){
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
        drawMethods(g2d, g2d.getFont(), shape.getBounds2D());
    }

    private void drawMethods(Graphics2D g, Font font, Rectangle2D r) {
        List<Method> methods = ((Klasa) element).getMethods();
        FontMetrics fm = g.getFontMetrics();
        int lineHeight = fm.getHeight();
        int padding = 5;
        int k = lineHeight * (((Klasa) element).getAttributes().size() + 1) + 4 * padding;

        for (Method method: methods){
            FontRenderContext frc = new FontRenderContext(null, true, true);

            Rectangle2D r2D = font.getStringBounds(method.getName(), frc);
            int rHeight = (int) Math.round(r2D.getHeight());

            int x = (int) r.getX() + 5;
            int y = (int) r.getY() + rHeight + k;

            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString(method.getName(), x, y);
            k = k + lineHeight;
        }
    }

    private void drawAttributes(Graphics2D g, Font font, Rectangle2D r) {
        List<Attribute> attributes = ((Klasa) element).getAttributes();
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

    private Shape createClassShape(Graphics g){
        FontMetrics fm = g.getFontMetrics();

        int lineHeight = fm.getHeight();
        int padding = 5;
        int headerHeight = lineHeight + 2 * padding;

        int attributesHeight = 10;
        int methodsHeight = 10;
        if(((Klasa) element).getAttributes() != null && !((Klasa) element).getAttributes().isEmpty())
            attributesHeight = (((Klasa) element).getAttributes().size() * lineHeight) + (((Klasa) element).getAttributes().size() - 1) * padding;

        if(((Klasa) element).getMethods() != null && !((Klasa) element).getMethods().isEmpty())
            methodsHeight = (((Klasa) element).getMethods().size() * lineHeight) + (((Klasa) element).getMethods().size() - 1) * padding;

        int totalHeight = headerHeight + attributesHeight + methodsHeight + padding;

        GeneralPath classShape = getGeneralPath(totalHeight, headerHeight, attributesHeight);

        return classShape;
    }

    private GeneralPath getGeneralPath(int totalHeight, int headerHeight, int attributesHeight) {
        double x = ((Klasa) element).getPosition().getX();
        double y = ((Klasa) element).getPosition().getY();
        double width = ((Klasa) element).getSize();
        Rectangle2D classRectangle = new Rectangle2D.Double(x, y, width, totalHeight);

        Line2D separator1 = new Line2D.Double(x, y + headerHeight, x + width, y + headerHeight);
        Line2D separator2 = new Line2D.Double(x, y + headerHeight + attributesHeight, x + width, y + headerHeight + attributesHeight);
        // Line2D separator1 = new Line2D.Double(x, y + headerHeight + methodsHeight, x + width, y + headerHeight + methodsHeight);

        GeneralPath classShape = new GeneralPath();
        classShape.append(classRectangle, false);
        classShape.append(separator1, false);
        classShape.append(separator2, false);
        return classShape;
    }
}
