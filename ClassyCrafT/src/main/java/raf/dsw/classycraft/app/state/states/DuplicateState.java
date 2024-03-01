package raf.dsw.classycraft.app.state.states;

import raf.dsw.classycraft.app.command.commands.Command;
import raf.dsw.classycraft.app.command.commands.DuplicateCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.PackageView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.InterclassPainter;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.*;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Enumeracija;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;
import raf.dsw.classycraft.app.state.State;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class DuplicateState implements State {
    @Override
    public void mousePressed(DiagramView diagramView, MouseEvent e) {
        //ne radi nista
    }

    @Override
    public void mouseDragged(DiagramView diagramView, MouseEvent e) {
        //ne radi nista
    }

    @Override
    public void mouseReleased(DiagramView diagramView, MouseEvent e) {
        /*
        Diagram diagram = (Diagram) ApplicationFramework.getInstance().getRepository().findDiagramByName(null, diagramView.getName());
        MainFrame.getInstance().getTree().setSelectedNode(diagram);
        if(diagramView.getSelectedArea() != null){
            if(diagramView.getSelectedArea().contains(e.getX(), e.getY())){
                List<Connection> conns = new ArrayList<>();
                List<Interclass> interclass = new ArrayList<>();
                for(ElementPainter ep : diagramView.getSelected()){
                    if(ep.getElement() instanceof Connection){
                        conns.add((Connection) ep.getElement());
                    }else if(ep.getElement() instanceof Interclass){
                        interclass.add((Interclass) ep.getElement());
                    }
                }
                for(Connection c : conns){
                    Interclass to = getDuplicateInterClass(c.getTo());
                    interclass.remove(c.getTo());
                    Interclass from = getDuplicateInterClass(c.getFrom());
                    interclass.remove(c.getFrom());
                    Connection conn = getDuplicateConnection(to, from, c);
                    ApplicationFramework.getInstance().getRepository().addChild(to);
                    ApplicationFramework.getInstance().getRepository().addChild(from);
                    ApplicationFramework.getInstance().getRepository().addChild(conn);
                }
                for(Interclass i : interclass){
                    Interclass copy = getDuplicateInterClass(i);
                    ApplicationFramework.getInstance().getRepository().addChild(copy);
                }
            }else{
               for(ElementPainter ep : diagramView.getPainterList()){
                   if(ep.getShape().contains(e.getX(),e.getY())){
                        if(ep.getElement() instanceof Interclass){
                            Interclass copy = getDuplicateInterClass((Interclass) ep.getElement());
                            ApplicationFramework.getInstance().getRepository().addChild(copy);
                        }else{
                            Interclass to = getDuplicateInterClass(((Connection) ep.getElement()).getTo());
                            Interclass from = getDuplicateInterClass(((Connection) ep.getElement()).getFrom());
                            Connection conn = getDuplicateConnection(to, from, (Connection) ep.getElement());
                            ApplicationFramework.getInstance().getRepository().addChild(to);
                            ApplicationFramework.getInstance().getRepository().addChild(from);
                            ApplicationFramework.getInstance().getRepository().addChild(conn);
                        }
                   }
               }
            }
            diagramView.getSelected().clear();
            diagramView.setSelectedArea(null);
        } else{
            for(ElementPainter ep : diagramView.getPainterList()){
                if(ep.getShape().contains(e.getX(),e.getY())){
                    if(ep.getElement() instanceof Interclass){
                        Interclass copy = getDuplicateInterClass((Interclass) ep.getElement());
                        ApplicationFramework.getInstance().getRepository().addChild(copy);
                    }else{
                        Interclass to = getDuplicateInterClass(((Connection) ep.getElement()).getTo());
                        Interclass from = getDuplicateInterClass(((Connection) ep.getElement()).getFrom());
                        Connection conn = getDuplicateConnection(to, from, (Connection) ep.getElement());
                        ApplicationFramework.getInstance().getRepository().addChild(to);
                        ApplicationFramework.getInstance().getRepository().addChild(from);
                        ApplicationFramework.getInstance().getRepository().addChild(conn);
                    }
                }
            }
        }

         */
        Command command = new DuplicateCommand(diagramView, e);
        ApplicationFramework.getInstance().getCommandManager().execute(command);
    }
}
