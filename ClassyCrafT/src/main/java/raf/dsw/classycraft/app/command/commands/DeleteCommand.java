package raf.dsw.classycraft.app.command.commands;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.Connection;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DeleteCommand extends Command{
    List<DiagramElement> elementsDeleted;

    public DeleteCommand(DiagramView diagramView, MouseEvent e) {
        super(diagramView, e);
        elementsDeleted = new ArrayList<>();
    }

    @Override
    public void execute() {
        Diagram diagram = (Diagram) ApplicationFramework.getInstance().getRepository().findDiagramByName(null, diagramView.getName());
        List<ElementPainter> painterList = diagramView.getPainterList();
        if(diagramView.getSelectedArea() != null) {
            if (diagramView.getSelectedArea().contains(e.getX(), e.getY())){
                List<ElementPainter> temp = diagramView.getSelected();
                List<Connection> toSkip = new ArrayList<>();
                for(ElementPainter ep : temp){
                    elementsDeleted.add(ep.getElement());

                    if(ep.getElement() instanceof Connection){
                        if(toSkip.contains((Connection) ep.getElement())) {
                            continue;
                        }
                    }
                    if(ep.getElement() instanceof Interclass){
                        for(ElementPainter epainter : temp){
                            if(epainter.getElement() instanceof Connection){
                                if(((Connection) epainter.getElement()).getFrom() == ep.getElement()
                                        || ((Connection)epainter.getElement()).getTo() == ep.getElement()){
                                    toSkip.add((Connection) epainter.getElement());
                                }
                            }
                        }
                    }
                    MainFrame.getInstance().getTree().setSelectedNode(ep.getElement());
                    ApplicationFramework.getInstance().getRepository().delete(diagram.getChildByName(ep.getElement().getName()));
                }
            } else {
                for (ElementPainter painter : painterList) {
                    if (painter.getShape().contains(e.getX(), e.getY())) {
                        elementsDeleted.add(painter.getElement());
                        MainFrame.getInstance().getTree().setSelectedNode(painter.getElement());
                        ApplicationFramework.getInstance().getRepository().delete(diagram.getChildByName(painter.getElement().getName()));
                    }
                }
            }
            diagramView.getSelected().clear();
            diagramView.setSelectedArea(null);
        } else {
            for (ElementPainter painter : painterList) {
                if (painter.getShape().contains(e.getX(), e.getY())) {
                    elementsDeleted.add(painter.getElement());
                    MainFrame.getInstance().getTree().setSelectedNode(painter.getElement());
                    ApplicationFramework.getInstance().getRepository().delete(diagram.getChildByName(painter.getElement().getName()));
                }
            }
        }


        diagram.setChanged(true);
    }

    @Override
    public void undo() {
        for(DiagramElement de : elementsDeleted){
            ApplicationFramework.getInstance().getRepository().addChild(de);
        }
    }

    @Override
    public void redo(){
        for (DiagramElement de : elementsDeleted){
            ApplicationFramework.getInstance().getRepository().delete(de);
        }
    }
}
