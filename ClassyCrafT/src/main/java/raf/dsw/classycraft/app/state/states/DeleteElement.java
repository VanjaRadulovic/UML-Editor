package raf.dsw.classycraft.app.state.states;

import raf.dsw.classycraft.app.command.commands.Command;
import raf.dsw.classycraft.app.command.commands.DeleteCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.connection.ConnectionPainter;
import raf.dsw.classycraft.app.repository.ElementType;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.Connection;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;
import raf.dsw.classycraft.app.state.State;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class DeleteElement implements State {

    @Override
    public void mousePressed(DiagramView diagramView, MouseEvent e) {
        Command command = new DeleteCommand(diagramView, e);
        ApplicationFramework.getInstance().getCommandManager().execute(command);
    }

    @Override
    public void mouseDragged(DiagramView diagramView, MouseEvent e) {

    }

    @Override
    public void mouseReleased(DiagramView diagramView, MouseEvent e) {

    }
}
