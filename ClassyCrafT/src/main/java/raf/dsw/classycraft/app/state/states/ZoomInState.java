package raf.dsw.classycraft.app.state.states;

import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.state.State;

import java.awt.event.MouseEvent;

public class ZoomInState implements State {

    @Override
    public void mousePressed(DiagramView diagramView, MouseEvent e) {
        diagramView.zoomIn(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(DiagramView diagramView, MouseEvent e) {

    }

    @Override
    public void mouseReleased(DiagramView diagramView, MouseEvent e) {

    }
}
