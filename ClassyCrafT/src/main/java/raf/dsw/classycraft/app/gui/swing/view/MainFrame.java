package raf.dsw.classycraft.app.gui.swing.view;


import raf.dsw.classycraft.app.gui.swing.controller.ActionManager;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.Tree;
import raf.dsw.classycraft.app.gui.swing.tree.TreeImplementation;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.gui.swing.view.bars.DiagramToolBar;
import raf.dsw.classycraft.app.gui.swing.view.bars.MyMenuBar;
import raf.dsw.classycraft.app.gui.swing.view.bars.Toolbar;
import raf.dsw.classycraft.app.gui.swing.view.views.PackageView;
import raf.dsw.classycraft.app.gui.swing.view.views.ProjectView;


import javax.swing.*;
import java.awt.*;
@Getter
@Setter
public class MainFrame extends JFrame{

    private ActionManager actionManager;
    private JMenuBar menu;
    private JToolBar toolBar;

    private JToolBar diagramToolBar;
    private Tree tree;
    private PackageView desktop;
    private JSplitPane split;
    private JScrollPane scroll;

    private void initialise() {
        actionManager = new ActionManager();
        tree = new TreeImplementation();
        initialiseGUI();
    }

    private void initialiseGUI() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("RuDok app");

        menu = new MyMenuBar();
        setJMenuBar(menu);

        toolBar = new Toolbar();
        add(toolBar, BorderLayout.NORTH);

        diagramToolBar = new DiagramToolBar();
        //getContentPane().add(diagramToolBar,BorderLayout.EAST);
        add(diagramToolBar, BorderLayout.EAST);

        JTree projectExplorer = tree.generateTree(ApplicationFramework.getInstance().getRepository().getProjectExplorer());

        desktop = new PackageView();
        desktop.setLayout(new BoxLayout(desktop, BoxLayout.Y_AXIS));
        ProjectView label = new ProjectView("<html>Ime: <br>Autor: </html>");
        MyTabbedPane tabbedPane = new MyTabbedPane();
        desktop.add(label);
        desktop.add(tabbedPane);

        scroll = new JScrollPane(projectExplorer);
        scroll.setMinimumSize(new Dimension(200,150));
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scroll,desktop);
        getContentPane().add(split,BorderLayout.CENTER);
        split.setDividerLocation(250);
        split.setOneTouchExpandable(true);
    }


    //Singleton

    private static MainFrame instance;

    private MainFrame(){

    }

    public static MainFrame getInstance(){
        if(instance==null){
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }
}
