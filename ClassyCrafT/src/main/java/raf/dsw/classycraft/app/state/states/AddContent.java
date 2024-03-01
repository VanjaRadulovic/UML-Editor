package raf.dsw.classycraft.app.state.states;

import org.w3c.dom.Attr;
import raf.dsw.classycraft.app.command.commands.AddContentCommand;
import raf.dsw.classycraft.app.command.commands.Command;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.ClassPainter;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.elements.content.ClassContent;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Enumeracija;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;
import raf.dsw.classycraft.app.state.State;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Attribute;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Method;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AddContent implements State {

    @Override
    public void mousePressed(DiagramView diagramView, MouseEvent e) {


    }

    @Override
    public void mouseDragged(DiagramView diagramView, MouseEvent e) {

    }

    @Override
    public void mouseReleased(DiagramView diagramView, MouseEvent e) {
        Command command = new AddContentCommand(diagramView, e);
        ApplicationFramework.getInstance().getCommandManager().execute(command);
    }
}
