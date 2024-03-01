package raf.dsw.classycraft.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.gui.swing.tree.model.TreeItem;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.PackageView;
import raf.dsw.classycraft.app.gui.swing.view.views.ProjectView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.connection.*;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.ClassPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.InterfacePainter;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.Project;
import raf.dsw.classycraft.app.repository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.*;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Enumeracija;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
@Setter
public class MyTabbedPane extends JTabbedPane {

    public MyTabbedPane() {

    }

    public void openPackage(TreeItem item){
        this.closeTabs();

        PackageView desktop = new PackageView();
        desktop.setLayout(new BoxLayout(desktop, BoxLayout.Y_AXIS));
        Node node = item.getNode();
        while(!(node instanceof Project)) node = node.getParent();
        ProjectView label = new ProjectView("<html>Ime: " + node.getName() +
                "<br>Autor: " + ((Project)node).getAuthor() + "</html>");
        desktop.add(label);
        desktop.add(this);

        ((IPublisher)item.getNode()).addSubscriber(desktop);
        MainFrame.getInstance().getSplit().setRightComponent(desktop);
        MainFrame.getInstance().setDesktop(desktop);


        this.updateInformation(item);
        for(Node node1 : ((NodeComposite) item.getNode()).getChildren()){
            if(node1 instanceof Diagram){
                DiagramView view = new DiagramView(node1.getName(), this);
                ((IPublisher) node1).addSubscriber(view);
                for(Node n : ((Diagram) node1).getChildren()){
                    DiagramElement de  = (DiagramElement) n;
                    ElementPainter ep = getPainter(de);
                    view.getPainterList().add(ep);
                }
                view.repaint();
                this.addTab(view.getName(), view);
            }
        }
    }

    private void closeTabs(){
        while (this.getTabCount() > 0) {
            this.removeTabAt(0);
        }
    }

    private void updateInformation(TreeItem item){
        ((IPublisher)item.getNode()).addSubscriber(this.getParent());
        for(Component c : this.getParent().getComponents()){
            if(c instanceof ProjectView){
                Node node = item.getNode();
                while(!(node instanceof Project)){
                    node = node.getParent();
                }
                ((ProjectView) c).setAuthor(((Project) node).getAuthor());
                c.setName(node.getName());
                ((IPublisher) node).addSubscriber(c);
            }
        }
    }

    private ElementPainter getPainter(DiagramElement obj){
        ElementPainter painter = null;
        if(obj instanceof Klasa) {
            painter = new ClassPainter((Klasa) obj);
        } else if(obj instanceof Interfejs){
            painter = new InterfacePainter((Interfejs) obj);
        } else if(obj instanceof Enumeracija){
            painter = new EnumPainter((Enumeracija) obj);
        } else if(obj instanceof Aggregation){
            painter = new AggregationPainter((Aggregation) obj);
        } else if(obj instanceof Generalization){
            painter = new GeneralizationPainter((Generalization) obj);
        } else if(obj instanceof Composition){
            painter = new CompositionPainter((Composition) obj);
        } else if(obj instanceof Dependence){
            painter = new DependencePainter((Dependence) obj);
        } else if(obj instanceof Connection){
            painter = new ConnectionPainter((Connection) obj);
        }

        return painter;
    }
}
