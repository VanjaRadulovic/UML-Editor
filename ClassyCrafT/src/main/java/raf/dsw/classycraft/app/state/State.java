package raf.dsw.classycraft.app.state;

import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface State {
    public void mousePressed(DiagramView diagramView, MouseEvent e);
    public void mouseDragged(DiagramView diagramView, MouseEvent e);
    public void mouseReleased(DiagramView diagramView, MouseEvent e);

}
