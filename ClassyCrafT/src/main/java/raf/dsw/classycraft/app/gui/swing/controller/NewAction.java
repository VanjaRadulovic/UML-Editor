package raf.dsw.classycraft.app.gui.swing.controller;


import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.error.ErrorType;
import raf.dsw.classycraft.app.gui.swing.tree.model.TreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.Package;
import raf.dsw.classycraft.app.repository.implementation.Project;
import raf.dsw.classycraft.app.repository.implementation.ProjectExplorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NewAction extends AbstractRudokAction {

    private String path="";
    private String author="";
    public NewAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/plus.png"));
        putValue(NAME, "New Project");
        putValue(SHORT_DESCRIPTION, "New Project");
    }

    public void actionPerformed(ActionEvent arg0) {
        TreeItem selected = (TreeItem) MainFrame.getInstance().getTree().getSelectedNode();

        if(selected == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.NO_NODE_SELECTED);
            return;
        }


        if(selected.getNode() instanceof Package){
            JDialog dialog = new JDialog(MainFrame.getInstance(), "Would like to add a Package or Diagram?", true);
            JButton option1Button = new JButton("Package");
            JButton option2Button = new JButton("Diagram");

            option1Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ApplicationFramework.getInstance().getRepository().addChild(
                            (NodeComposite) selected.getNode(), "Package " +new Random().nextInt(100),author, path, true);
                    dialog.dispose();
                }
            });

            option2Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ApplicationFramework.getInstance().getRepository().addChild(
                            (NodeComposite) selected.getNode(), "Diagram " +new Random().nextInt(100),author, path, false);
                    dialog.dispose();
                }
            });

            JPanel panel = new JPanel();
            panel.add(option1Button);
            panel.add(option2Button);


            dialog.getContentPane().add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(MainFrame.getInstance());
            dialog.setSize(300, 100);
            dialog.setVisible(true);
        }
        if(selected.getNode() instanceof ProjectExplorer){
                JDialog dialog = new JDialog(MainFrame.getInstance(), "Unesite odgovarajuce podatke", true);

                JTextField textField1 = new JTextField(20); // First input text field
                JTextField textField2 = new JTextField(20); // Second input text field

                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        author = textField1.getText();
                        path = textField2.getText();
                        author = author.trim();
                        path = path.trim();
                        if (!author.equals("") || !path.equals("")) {
                            author = author.trim();
                            path = path.trim();
                            ApplicationFramework.getInstance().getRepository().addChild(
                                    (NodeComposite) selected.getNode(), "Project " + new Random().nextInt(100), author, path, false);
                        }
                        else {
                            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.NAME_CANNOT_BE_EMPTY);
                        }
                        dialog.dispose();
                    }
                });

                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        return;
                    }
                });

                JPanel panel = new JPanel(new GridLayout(3, 2));
                panel.add(new JLabel("Autora:"));
                panel.add(textField1);
                panel.add(new JLabel("Putanja:"));
                panel.add(textField2);
                panel.add(okButton);

                dialog.getContentPane().add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(MainFrame.getInstance());
                dialog.setVisible(true);



        }
        if(selected.getNode() instanceof Project)
            ApplicationFramework.getInstance().getRepository().addChild(
                    (NodeComposite) selected.getNode(),"Package " +new Random().nextInt(100),author, path,false);

        if(selected.getNode() instanceof Diagram)
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.CANNOT_ADD_TO_DIAGRAM);

    }

}
