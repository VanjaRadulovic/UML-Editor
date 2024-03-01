package raf.dsw.classycraft.app.gui.swing.view.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.connection.*;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.ClassPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.interclass.InterfacePainter;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.repository.ActionType;
import raf.dsw.classycraft.app.repository.implementation.DiagramElement;
import raf.dsw.classycraft.app.repository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.repository.implementation.elements.connection.*;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Enumeracija;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class DiagramView extends JPanel implements ISubscriber {
    private String name;
    private JTabbedPane pane;
    private List<ElementPainter> painterList;
    private List<ElementPainter> selected;
    private Rectangle2D selectedArea;

    private float screenScailingFactor =1.7f;
    private double scalingFactor = 1.3;
    private double scaling = 1;

    private Double xMove = 0.0;
    private Double yMove = 0.0;

    private Double xStart = 0.0;
    private Double yStart = 0.0;


    public DiagramView(String name, JTabbedPane pane){
        this.name = name;
        this.pane = pane;
        this.painterList = new ArrayList<>();
        this.selected = new ArrayList<>();
        this.selectedArea = null;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ((PackageView) pane.getParent()).mousePressed(DiagramView.this, e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ((PackageView) pane.getParent()).mouseReleased(DiagramView.this, e);
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                ((PackageView) pane.getParent()).mouseDragged(DiagramView.this, e);
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        AffineTransform affineTransform = AffineTransform.getTranslateInstance(xMove, yMove);
        affineTransform.scale(scaling, scaling);
        g2.transform(affineTransform);

        AffineTransform inverseTransform;
        try {
            inverseTransform = affineTransform.createInverse();
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
            return;
        }

        for (ElementPainter painter : painterList) {
            painter.setTransform(inverseTransform);
            painter.paint(g);
        }


        if (selectedArea != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));

            g2d.draw(inverseTransform.createTransformedShape(selectedArea).getBounds2D());
        }
    }


    @Override
    public void update(Object obj, Enum e) {
        if(obj instanceof String) {
            String text = ((String) obj);
            if (text.contains("Name")) {
                String name = text.replace("Name:", "");
                for (int i = 0; i < pane.getTabCount(); i++) {
                    DiagramView tabComponent = (DiagramView) pane.getComponentAt(i);
                    if (tabComponent.getName().equals(this.name)) {
                        pane.setTitleAt(i, name);
                        this.setName(name);
                    }
                }
            }
        } else if (obj instanceof DiagramElement && e.equals(ActionType.ADDELEMENT)){
            if(obj instanceof Klasa) {
                ClassPainter painter = new ClassPainter((Klasa) obj);
                this.getPainterList().add(painter);
                this.repaint();
            }else if(obj instanceof Interfejs){
                InterfacePainter painter = new InterfacePainter((Interfejs) obj);
                this.getPainterList().add(painter);
                this.repaint();
            }else if(obj instanceof Enumeracija){
                EnumPainter painter = new EnumPainter((Enumeracija) obj);
                this.getPainterList().add(painter);
                this.repaint();
            }
            else if(obj instanceof Aggregation){
                AggregationPainter painter = new AggregationPainter((Aggregation) obj);
                this.getPainterList().add(painter);
                this.repaint();
            } else if(obj instanceof Generalization){
                GeneralizationPainter painter = new GeneralizationPainter((Generalization) obj);
                this.getPainterList().add(painter);
                this.repaint();
            } else if(obj instanceof Composition){
                CompositionPainter painter = new CompositionPainter((Composition) obj);
                this.getPainterList().add(painter);
                this.repaint();
            } else if(obj instanceof Dependence){
                DependencePainter painter = new DependencePainter((Dependence) obj);
                this.getPainterList().add(painter);
                this.repaint();
            } else if(obj instanceof Connection){
                ConnectionPainter painter = new ConnectionPainter((Connection) obj);
                this.getPainterList().add(painter);
                this.repaint();
            }
        } else if (obj instanceof DiagramElement && e.equals(ActionType.MODIFYELEMENT)){
            painterList  = painterList.stream()
                    .filter(painter -> !painter.getElement().equals(obj))
                    .collect(Collectors.toList());
            if(obj instanceof Klasa){
                ClassPainter painter = new ClassPainter((Klasa) obj);
                this.getPainterList().add(painter);
                this.repaint();
            } else if (obj instanceof Interfejs){
                InterfacePainter painter = new InterfacePainter((Interfejs) obj);
                this.getPainterList().add(painter);
                this.repaint();
            } else if (obj instanceof Enumeracija){
                EnumPainter painter = new EnumPainter((Enumeracija) obj);
                this.getPainterList().add(painter);
                this.repaint();
            }
        } else if(obj instanceof DiagramElement && e.equals(ActionType.DELETEELEMENT)) {
            painterList = painterList.stream()
                    .filter(painter -> !painter.getElement().equals(obj))
                    .collect(Collectors.toList());
            this.repaint();
        } else if(obj instanceof DiagramElement && e.equals(ActionType.MODIFYCONNECTION)){
            painterList = painterList.stream()
                    .filter(painter -> !painter.getElement().equals(obj))
                    .collect(Collectors.toList());
            if(obj instanceof Aggregation){
                AggregationPainter painter = new AggregationPainter((Aggregation) obj);
                this.getPainterList().add(painter);
                this.repaint();
            } else if(obj instanceof Generalization){
                GeneralizationPainter painter = new GeneralizationPainter((Generalization) obj);
                this.getPainterList().add(painter);
                this.repaint();
            } else if(obj instanceof Composition){
                CompositionPainter painter = new CompositionPainter((Composition) obj);
                this.getPainterList().add(painter);
                this.repaint();
            } else if(obj instanceof Dependence){
                DependencePainter painter = new DependencePainter((Dependence) obj);
                this.getPainterList().add(painter);
                this.repaint();
            } else if (obj instanceof Connection) {
                ConnectionPainter painter = new ConnectionPainter((Connection) obj);
                this.getPainterList().add(painter);
                this.repaint();
            }
        }
    }

    public void zoomIn(int clickX, int clickY) {

        xMove = (1 - scalingFactor) * clickX + scalingFactor * xMove;
        yMove = (1 - scalingFactor) * clickY + scalingFactor * yMove;

        if (scaling > 5) {
            scaling = 5;
            repaint();
            return;
        }
        scaling *= scalingFactor;
        repaint();
    }

    public void zoomOut(int clickX, int clickY) {

        SwingUtilities.invokeLater(() -> {
            xMove = (1 - 1 / scalingFactor) * clickX + (1 / scalingFactor) * xMove;
            yMove = (1 - 1 / scalingFactor) * clickY + (1 / scalingFactor) * yMove;

            scaling /= scalingFactor;

            revalidate();
            repaint();
        });
    }

    public void Move(double x, double y){
        xMove = x-xStart;
        yMove = y-yStart;
        repaint();
    }
}
