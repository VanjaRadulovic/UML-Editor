package raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass;

import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public abstract class InterclassPainter extends ElementPainter {

    public InterclassPainter(DiagramElement element) {
        super(element);
    }

    public void drawString(Graphics2D g, Font font, Rectangle2D r, String s) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rHeight = (int) Math.round(r2D.getHeight());

        int x = (int) r.getX() + 5;
        int y = (int) r.getY() + rHeight;

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(s, x, y);
    }
}
