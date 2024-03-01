package raf.dsw.classycraft.app.state.states;
import raf.dsw.classycraft.app.command.commands.AddCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.error.ErrorType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.ClassPainter;
import raf.dsw.classycraft.app.repository.ElementType;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;
import raf.dsw.classycraft.app.state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class    AddElement implements State{

    @Override
    public void mousePressed(DiagramView diagramView, MouseEvent e) {
        AddCommand command = new AddCommand(diagramView,e);
        ApplicationFramework.getInstance().getCommandManager().execute(command);
    }

    @Override
    public void mouseDragged(DiagramView diagramView, MouseEvent e) {
        //Ne radi nista
    }

    @Override
    public void mouseReleased(DiagramView diagramView, MouseEvent e) {
    }
}
