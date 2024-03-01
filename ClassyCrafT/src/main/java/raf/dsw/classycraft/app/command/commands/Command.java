package raf.dsw.classycraft.app.command.commands;

import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;

import java.awt.event.MouseEvent;

public abstract class Command {
    protected DiagramView diagramView;
    protected MouseEvent e;

    public Command(DiagramView diagramView, MouseEvent e) {
        this.diagramView = diagramView;
        this.e = e;
    }

    public abstract void execute();
    public abstract void undo();
    public abstract void redo();
}
