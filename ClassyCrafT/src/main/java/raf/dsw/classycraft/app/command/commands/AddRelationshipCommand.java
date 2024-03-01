package raf.dsw.classycraft.app.command.commands;

import jdk.jshell.Diag;
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class AddRelationshipCommand extends Command{
    private int option;
    private Interclass to;
    private Interclass from;

    public AddRelationshipCommand(DiagramView diagramView, MouseEvent e) {
        super(diagramView, e);
    }

    @Override
    public void execute() {
        Diagram diagram = (Diagram) ApplicationFramework.getInstance().getRepository().findDiagramByName(null, diagramView.getName());
        MainFrame.getInstance().getTree().setSelectedNode(diagram);
        Connection connection = (Connection) diagram.getChildByName("inProgress");
        List<ElementPainter> painterList = diagramView.getPainterList();
        for(ElementPainter painter : painterList){
            Shape shape = painter.getShape();
            if (shape != null && shape.contains(e.getX(), e.getY())) {
                if(!(painter instanceof InterclassPainter)) {
                    JOptionPane.showMessageDialog(null, "Connection must end at Interclass.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.INVALID_CONNECTION);
                    MainFrame.getInstance().getTree().setSelectedNode(connection);
                    ApplicationFramework.getInstance().getRepository().delete(connection);
                    return;
                }
                switch(showPopup()){
                    case 0:
                        ApplicationFramework.getInstance().getRepository().addChild(diagram, "f: " + connection.getFrom().getName()
                                        + " t: " + painter.getElement().getName(), connection.getFrom(),
                                (Interclass) painter.getElement(), connection.getFrom().getPosition(), ((Interclass) painter.getElement()).getPosition(), ElementType.COMPOSITION);
                        option = 0;
                        from = connection.getFrom();
                        to = (Interclass) painter.getElement();
                        MainFrame.getInstance().getTree().setSelectedNode(connection);
                        ApplicationFramework.getInstance().getRepository().delete(connection);
                        return;
                    case 1:
                        ApplicationFramework.getInstance().getRepository().addChild(diagram, "f: " + connection.getFrom().getName()
                                        + " t: " + painter.getElement().getName(), connection.getFrom(),
                                (Interclass) painter.getElement(), connection.getFrom().getPosition(), ((Interclass) painter.getElement()).getPosition(), ElementType.AGGREGATION);
                        option = 1;
                        from = connection.getFrom();
                        to = (Interclass) painter.getElement();
                        MainFrame.getInstance().getTree().setSelectedNode(connection);
                        ApplicationFramework.getInstance().getRepository().delete(connection);
                        return;
                    case 2:
                        ApplicationFramework.getInstance().getRepository().addChild(diagram, "f: " + connection.getFrom().getName()
                                        + " t: " + painter.getElement().getName(), connection.getFrom(),
                                (Interclass) painter.getElement(), connection.getFrom().getPosition(), ((Interclass) painter.getElement()).getPosition(), ElementType.DEPENDENCE);
                        option = 2;
                        from = connection.getFrom();
                        to = (Interclass) painter.getElement();
                        MainFrame.getInstance().getTree().setSelectedNode(connection);
                        ApplicationFramework.getInstance().getRepository().delete(connection);
                        return;
                    case 3:
                        ApplicationFramework.getInstance().getRepository().addChild(diagram, "f: " + connection.getFrom().getName()
                                        + " t: " + painter.getElement().getName(), connection.getFrom(),
                                (Interclass) painter.getElement(), connection.getFrom().getPosition(), ((Interclass) painter.getElement()).getPosition(), ElementType.GENERALIZATION);
                        option = 3;
                        from = connection.getFrom();
                        to = (Interclass) painter.getElement();
                        MainFrame.getInstance().getTree().setSelectedNode(connection);
                        ApplicationFramework.getInstance().getRepository().delete(connection);
                        return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Connection must end at entity.",
                "Error", JOptionPane.ERROR_MESSAGE);
        ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.INVALID_CONNECTION);
        MainFrame.getInstance().getTree().setSelectedNode(connection);
        ApplicationFramework.getInstance().getRepository().delete(connection);

        diagram.setChanged(true);
    }

    @Override
    public void undo() {
        Diagram diagram = (Diagram) ApplicationFramework.getInstance().getRepository().findDiagramByName(null, diagramView.getName());
        String name = "f: " + from.getName()
                + " t: " + to.getName();
        MainFrame.getInstance().getTree().setSelectedNode(diagram.getChildByName(name));
        ApplicationFramework.getInstance().getRepository().delete(diagram.getChildByName(name));
    }

    @Override
    public void redo() {
        Diagram diagram = (Diagram) ApplicationFramework.getInstance().getRepository().findDiagramByName(null, diagramView.getName());
        MainFrame.getInstance().getTree().setSelectedNode(diagram);

        switch(option){
            case 0:
                ApplicationFramework.getInstance().getRepository().addChild(diagram, "f: " + from.getName()
                                + " t: " + to.getName(), from,
                        to, from.getPosition(), to.getPosition(), ElementType.COMPOSITION);
                return;
            case 1:
                ApplicationFramework.getInstance().getRepository().addChild(diagram, "f: " + from.getName()
                                + " t: " + to.getName(), from,
                        to, from.getPosition(), to.getPosition(), ElementType.AGGREGATION);
                return;
            case 2:
                ApplicationFramework.getInstance().getRepository().addChild(diagram, "f: " + from.getName()
                                + " t: " + to.getName(), from,
                        to, from.getPosition(), to.getPosition(), ElementType.DEPENDENCE);
                return;
            case 3:
                ApplicationFramework.getInstance().getRepository().addChild(diagram, "f: " + from.getName()
                                + " t: " + to.getName(), from,
                        to, from.getPosition(), to.getPosition(), ElementType.GENERALIZATION);
        }
    }

    public int showPopup() {
        String[] choices = {"COMPOSITION", "AGGREGATION", "DEPENDENCE", "GENERALIZATION"};

        int choice = JOptionPane.showOptionDialog(null,
                "Choose realationship type",
                "Choose realationship type",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                choices[0]);

        if (choice >= 0 && choice < choices.length) {
            return choice;
        } else {
            return -1;
            //TODO error
        }
    }
}
