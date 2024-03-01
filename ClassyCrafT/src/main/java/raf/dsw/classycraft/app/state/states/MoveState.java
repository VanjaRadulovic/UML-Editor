package raf.dsw.classycraft.app.state.states;

import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.state.State;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class MoveState implements State {
    @Override
    public void mousePressed(DiagramView diagramView, MouseEvent e) {
        diagramView.setXStart((double) e.getX());
        diagramView.setYStart((double) e.getY());
        diagramView.setXMove((double) 0);
        diagramView.setYMove((double) 0);
    }

    @Override
    public void mouseDragged(DiagramView diagramView, MouseEvent e) {
            diagramView.Move(e.getX(),e.getY());
    }

    @Override
    public void mouseReleased(DiagramView diagramView, MouseEvent e) {

    }
}
