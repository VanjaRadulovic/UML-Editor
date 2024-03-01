package raf.dsw.classycraft.app.state.states;
import raf.dsw.classycraft.app.command.commands.AddRelationshipCommand;
import raf.dsw.classycraft.app.command.commands.Command;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.error.ErrorType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.InterclassPainter;
import raf.dsw.classycraft.app.repository.ElementType;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.Connection;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import raf.dsw.classycraft.app.state.State;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class AddRelationship implements State{


    @Override
    public void mousePressed(DiagramView diagramView, MouseEvent e) {
        Diagram diagram = (Diagram) ApplicationFramework.getInstance().getRepository().findDiagramByName(null, diagramView.getName());
        List<ElementPainter> painterList = diagramView.getPainterList();
        int idx = -1;
        for(ElementPainter painter : painterList){
            Shape shape = painter.getShape();
            if (shape != null && shape.contains(e.getX(), e.getY())) {
                if(!(painter.getElement() instanceof Interclass)){
                    JOptionPane.showMessageDialog(null, "Connection must start at Interclass.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.INVALID_CONNECTION);
                    return;
                }
                idx = painterList.indexOf(painter);
            }
        }
        if(idx != -1) {
            ApplicationFramework.getInstance().getRepository().addChild(diagram, "inProgress", (Interclass) painterList.get(idx).getElement(),
                    null, new Point(e.getX(), e.getY()), new Point(e.getX(), e.getY()), ElementType.CONNECTION);
        } else{
            JOptionPane.showMessageDialog(null, "Connection must start at element.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.INVALID_CONNECTION);
        }
    }

    @Override
    public void mouseDragged(DiagramView diagramView, MouseEvent e) {
        Diagram diagram = (Diagram) ApplicationFramework.getInstance().getRepository().findDiagramByName(null, diagramView.getName());
        Connection connection = (Connection) diagram.getChildByName("inProgress");
        connection.changeEnd(new Point(e.getX(), e.getY()));
    }

    @Override
    public void mouseReleased(DiagramView diagramView, MouseEvent e) {
        Command command = new AddRelationshipCommand(diagramView, e);
        ApplicationFramework.getInstance().getCommandManager().execute(command);
    }
}

