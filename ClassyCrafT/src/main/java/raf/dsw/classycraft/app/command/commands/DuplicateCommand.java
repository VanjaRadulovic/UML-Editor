package raf.dsw.classycraft.app.command.commands;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.*;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Enumeracija;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interclass;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DuplicateCommand extends Command{
    List<DiagramElement> duplicateConnetionList;
    List<DiagramElement> duplicateInterclassList;
    public DuplicateCommand(DiagramView diagramView, MouseEvent e) {
        super(diagramView, e);
        duplicateConnetionList = new ArrayList<>();
        duplicateInterclassList = new ArrayList<>();
    }

    @Override
    public void execute() {
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
                    duplicateInterclassList.add(to);
                    interclass.remove(c.getTo());
                    Interclass from = getDuplicateInterClass(c.getFrom());
                    duplicateInterclassList.add(from);
                    interclass.remove(c.getFrom());
                    Connection conn = getDuplicateConnection(to, from, c);
                    duplicateConnetionList.add(conn);
                    ApplicationFramework.getInstance().getRepository().addChild(to);
                    ApplicationFramework.getInstance().getRepository().addChild(from);
                    ApplicationFramework.getInstance().getRepository().addChild(conn);
                }
                for(Interclass i : interclass){
                    Interclass copy = getDuplicateInterClass(i);
                    duplicateInterclassList.add(copy);
                    ApplicationFramework.getInstance().getRepository().addChild(copy);
                }
            }else{
                for(ElementPainter ep : diagramView.getPainterList()){
                    if(ep.getShape().contains(e.getX(),e.getY())){
                        if(ep.getElement() instanceof Interclass){
                            Interclass copy = getDuplicateInterClass((Interclass) ep.getElement());
                            duplicateInterclassList.add(copy);
                            ApplicationFramework.getInstance().getRepository().addChild(copy);
                        }else{
                            Interclass to = getDuplicateInterClass(((Connection) ep.getElement()).getTo());
                            duplicateInterclassList.add(to);
                            Interclass from = getDuplicateInterClass(((Connection) ep.getElement()).getFrom());
                            duplicateInterclassList.add(from);
                            Connection conn = getDuplicateConnection(to, from, (Connection) ep.getElement());
                            duplicateConnetionList.add(conn);
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
                        duplicateInterclassList.add(copy);
                        ApplicationFramework.getInstance().getRepository().addChild(copy);
                    }else{
                        Interclass to = getDuplicateInterClass(((Connection) ep.getElement()).getTo());
                        duplicateInterclassList.add(to);
                        Interclass from = getDuplicateInterClass(((Connection) ep.getElement()).getFrom());
                        duplicateInterclassList.add(from);
                        Connection conn = getDuplicateConnection(to, from, (Connection) ep.getElement());
                        duplicateConnetionList.add(conn);
                        ApplicationFramework.getInstance().getRepository().addChild(to);
                        ApplicationFramework.getInstance().getRepository().addChild(from);
                        ApplicationFramework.getInstance().getRepository().addChild(conn);
                    }
                }
            }
        }


        diagram.setChanged(true);
    }

    @Override
    public void undo() {
        List<DiagramElement> temp = new ArrayList<>();
        for(DiagramElement element : duplicateInterclassList){
            for(DiagramElement de : duplicateConnetionList){
                if(((Connection) de).getFrom() == element || ((Connection) de).getTo() == element){
                    temp.add(de);
                }
            }
            ApplicationFramework.getInstance().getRepository().delete(element);
        }

        for(DiagramElement element : duplicateConnetionList){
            if(!temp.contains(element)) {
                ApplicationFramework.getInstance().getRepository().delete(element);
            }
        }
    }

    @Override
    public void redo() {
        for(DiagramElement element : duplicateInterclassList){
            ApplicationFramework.getInstance().getRepository().addChild(element);
        }

        for(DiagramElement element : duplicateConnetionList){
            ApplicationFramework.getInstance().getRepository().addChild(element);
        }
    }

    private Interclass getDuplicateInterClass(Interclass interclass){
        if (interclass instanceof Klasa){
            Klasa copy =  new Klasa("Copy of " + interclass.getName(), interclass.getParent(), interclass.getVisibility(),
                    new Point((int) (interclass.getPosition().getX() + 30), (int) (interclass.getPosition().getY() + 30)), interclass.getSize());
            copy.setAttributes(((Klasa) interclass).getAttributes());
            copy.setMethods(((Klasa) interclass).getMethods());
            return copy;
        } else if(interclass instanceof Interfejs){
            Interfejs copy = new Interfejs("Copy of " + interclass.getName(), interclass.getParent(), interclass.getVisibility(),
                    new Point((int) (interclass.getPosition().getX() + 30), (int) (interclass.getPosition().getY() + 30)), interclass.getSize());
            copy.setMethods(((Interfejs) interclass).getMethods());
            return copy;
        } else {
            Enumeracija copy = new Enumeracija("Copy of " + interclass.getName(), interclass.getParent(), interclass.getVisibility(),
                    new Point((int) (interclass.getPosition().getX() + 30), (int) (interclass.getPosition().getY() + 30)), interclass.getSize());
            copy.setAttributes(((Enumeracija)interclass).getAttributes());
            return copy;
        }
    }

    private Connection getDuplicateConnection(Interclass to, Interclass from, Connection conn){
        if(conn instanceof Aggregation){
            return new Aggregation("Copy of " + conn.getName(), conn.getParent(),from, to, from.getPosition(), to.getPosition());
        }else if(conn instanceof Generalization){
            return new Generalization("Copy of " + conn.getName(), conn.getParent(),from, to, from.getPosition(), to.getPosition());
        }else if(conn instanceof Dependence){
            return new Dependence("Copy of " + conn.getName(), conn.getParent(),from, to, from.getPosition(), to.getPosition());
        }else if(conn instanceof Composition){
            return new Composition("Copy of " + conn.getName(), conn.getParent(),from, to, from.getPosition(), to.getPosition());
        }
        return null;
    }
}
