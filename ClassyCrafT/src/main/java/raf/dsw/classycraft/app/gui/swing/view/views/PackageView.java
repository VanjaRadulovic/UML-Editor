package raf.dsw.classycraft.app.gui.swing.view.views;

import com.sun.tools.javac.Main;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.state.StateManager;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.repository.implementation.Diagram;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.MouseEvent;

@Getter
@Setter
public class PackageView extends JPanel implements ISubscriber {

    public PackageView(){}

    @Override
    public void update(Object obj, Enum e) {
        if(obj instanceof Diagram){
            for (Component c : this.getComponents()) {
                if (c instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) c;
                    Component[] tabs = pane.getComponents();
                    DiagramView view = new DiagramView(((Diagram) obj).getName(), pane);
                    pane.removeAll();
                    ((Diagram) obj).addSubscriber(view);
                    for(Component comp : tabs){
                        pane.add(comp);
                    }
                    pane.addTab(view.getName(), view);
                }
        }
        } else {
            String text = (String) obj;
            if (text.contains("delete:")) {
                String name = text.replace("delete:", "");
                for (Component c : this.getComponents()) {
                    if (c instanceof JTabbedPane) {
                        JTabbedPane pane = (JTabbedPane) c;
                        for (int i = 0; i < pane.getTabCount(); i++) {
                            DiagramView tabComponent = (DiagramView) pane.getComponentAt(i);
                            if (tabComponent.getName().equals(name)) {
                                pane.remove(i);
                            }
                        }
                    }
                }
            } else {
                if (text.contains("DELETE")) {
                    for (Component c : this.getComponents()) {
                        if (c instanceof JTabbedPane) {
                            ((JTabbedPane) c).removeAll();
                        }
                    }
                }
            }
        }
    }

    public void mousePressed(DiagramView diagramView, MouseEvent e){
        ApplicationFramework.getInstance().getStateManager().getCurrent().mousePressed(diagramView, e);
    }

    public void mouseDragged(DiagramView diagramView, MouseEvent e){
        ApplicationFramework.getInstance().getStateManager().getCurrent().mouseDragged(diagramView, e);
    }

    public void mouseReleased(DiagramView diagramView, MouseEvent e){
        ApplicationFramework.getInstance().getStateManager().getCurrent().mouseReleased(diagramView, e);
    }

    public void changeToAddElementState(){
        ApplicationFramework.getInstance().getStateManager().setAddElementState();
    }
    public void changeToDeleteElementState(){
        ApplicationFramework.getInstance().getStateManager().setDeleteElementState();
    }
    public void changeToAddRelationshipState(){
        ApplicationFramework.getInstance().getStateManager().setAddRelationshipState();
    }
    public void changeToAddContentState(){
        ApplicationFramework.getInstance().getStateManager().setAddContentState();
    }

    public void changeToSelectState(){ApplicationFramework.getInstance().getStateManager().setSelectState();}

    public void changeToZoomInState(){
        ApplicationFramework.getInstance().getStateManager().setZoomInState();
    }
    public void changeToZoomOutState(){
        ApplicationFramework.getInstance().getStateManager().setZoomOutState();
    }
    public void changeToDuplicateState(){ApplicationFramework.getInstance().getStateManager().setDuplicateState();}

    public void changeToMoveState(){
        ApplicationFramework.getInstance().getStateManager().setMoveState();
    }
}
