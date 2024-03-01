package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.error.ErrorType;
import raf.dsw.classycraft.app.gui.swing.tree.model.TreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;
import raf.dsw.classycraft.app.repository.implementation.Project;
import raf.dsw.classycraft.app.repository.implementation.ProjectExplorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class EditAction extends AbstractRudokAction{

    private String path;
    private String author;
    public EditAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/edit.png"));
        putValue(NAME, "Edit");
        putValue(SHORT_DESCRIPTION, "Edit");
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        TreeItem selected = (TreeItem) MainFrame.getInstance().getTree().getSelectedNode();
        if(selected == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.NO_NODE_SELECTED);
            return;
        }
        NodeComposite node = (NodeComposite) selected.getNode();
        if(node instanceof Project) {

            JDialog dialog = new JDialog(MainFrame.getInstance(), "Unesite odgovarajuce podatke", true);

            JTextField textField1 = new JTextField(20); // First input text field
            JTextField textField2 = new JTextField(20); // Second input text field
            author = ((Project) node).getAuthor();
            path = ((Project) node).getPath();
            textField1.setText(((Project) node).getAuthor());
            textField2.setText(((Project) node).getPath());
            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    author = textField1.getText();
                    path = textField2.getText();
                    author = author.trim();
                    path = path.trim();
                    if (!author.equals("") || !path.equals("")) {
                        ApplicationFramework.getInstance().getRepository().edit(node, author, path);
                    } else {
                        ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.NAME_CANNOT_BE_EMPTY);
                    }
                    dialog.dispose();
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
        else
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.MUST_SELECT_PROJECT);

    }
}
