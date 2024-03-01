package raf.dsw.classycraft.app.state.states;

import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.Connection;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import raf.dsw.classycraft.app.state.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class SelectState implements State {
    private boolean dragging = false;
    private boolean deselectFlag = false;
    private Point startPoint;
    private Point endPoint;

    @Override
    public void mousePressed(DiagramView diagramView, MouseEvent e) {
        if(diagramView.getSelectedArea() != null) { //postoje selectovani elementi
            if (diagramView.getSelectedArea().contains(e.getX(), e.getY())) {
                startPoint = new Point(e.getX(), e.getY());
                this.dragging = true;
            } else{
                //kliknuo van, deselect
                this.dragging = false;
                this.deselectFlag = true;
            }
        }else{ //ne postoje selectovani elementi treba praviti novi rectangle
            this.dragging = false;
            startPoint = new Point(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseDragged(DiagramView diagramView, MouseEvent e) {
        if(dragging){
            double deltaX = e.getX() - startPoint.getX();
            double deltaY = e.getY() - startPoint.getY();

            for(ElementPainter ep : diagramView.getSelected()){
                if(ep.getElement() instanceof Interclass){
                    Point newPosition = new Point((int) (((Interclass) ep.getElement()).getPosition().getX() +deltaX),
                            (int) (((Interclass) ep.getElement()).getPosition().getY() + deltaY));
                    ((Interclass) ep.getElement()).setPosition(newPosition);
                }
            }

            for(ElementPainter ep : diagramView.getPainterList()){
                if(ep.getElement() instanceof Connection){
                    ((Connection) ep.getElement()).changeStart(((Connection) ep.getElement()).getFrom().getPosition());
                    ((Connection) ep.getElement()).changeEnd(((Connection) ep.getElement()).getTo().getPosition());
                }
            }

            Rectangle2D newRectangle = new Rectangle2D.Double(
                    diagramView.getSelectedArea().getX() + deltaX,
                    diagramView.getSelectedArea().getY() + deltaY,
                    diagramView.getSelectedArea().getWidth(),
                    diagramView.getSelectedArea().getHeight()
            );
            diagramView.setSelectedArea(newRectangle);

            diagramView.repaint();

            startPoint = new Point(e.getX(), e.getY());
        } else if (deselectFlag == false){
            endPoint = new Point(e.getX(), e.getY());

            double x = Math.min(startPoint.getX(), endPoint.getX());
            double y = Math.min(startPoint.getY(), endPoint.getY());
            double width = Math.abs(startPoint.getX() - endPoint.getX());
            double height = Math.abs(startPoint.getY() - endPoint.getY());

            Rectangle2D selectionRectangle = new Rectangle2D.Double(x, y, width, height);


            diagramView.setSelectedArea(selectionRectangle);
            diagramView.repaint();
        }
    }

    @Override
    public void mouseReleased(DiagramView diagramView, MouseEvent e) {
        if(dragging){
            dragging= false;
        }else{
            if(deselectFlag){
                deselectFlag = false;
                diagramView.getSelected().clear();
                diagramView.setSelectedArea(null);
                diagramView.repaint();
            }else{
                endPoint = new Point(e.getX(), e.getY());
                double x = Math.min(startPoint.getX(), endPoint.getX());
                double y = Math.min(startPoint.getY(), endPoint.getY());
                double width = Math.abs(startPoint.getX() - endPoint.getX());
                double height = Math.abs(startPoint.getY() - endPoint.getY());

                Rectangle2D selectionRectangle = new Rectangle2D.Double(x, y, width, height);

                diagramView.setSelectedArea(selectionRectangle);

                for (ElementPainter ep : diagramView.getPainterList()){
                    if(ep.getShape().intersects(selectionRectangle)){
                        diagramView.getSelected().add(ep);
                    }
                }

                diagramView.repaint();
            }
        }
    }
}
