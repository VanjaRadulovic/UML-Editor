package raf.dsw.classycraft.app.gui.swing.controller;

import com.sun.tools.javac.Main;
import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.Project;
import raf.dsw.classycraft.app.repository.implementation.ProjectExplorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class SaveAsAction extends AbstractRudokAction {
    private String option;

    public SaveAsAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/save_as.png"));
        putValue(NAME, "SaveAsAction");
        putValue(SHORT_DESCRIPTION, "SaveAsAction");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showTypeDialog();
        Node node;
        switch(option) {
            case "Project":
                node = MainFrame.getInstance().getTree().getSelectedNode().getNode();
                if (node instanceof ProjectExplorer){
                    JOptionPane.showMessageDialog(null, "Please select project or child of project", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                    }
                while(!(node instanceof Project)){
                    node = node.getParent();
                }
                if(!node.isChanged()){
                    JOptionPane.showMessageDialog(null, "Already saved.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                File file = showFileDialog(node.getName());
                if(file == null){
                    break;
                }else {
                    ApplicationFramework.getInstance().getSerializer().serializeToJSON((Project) node, file);
                }
                break;
            case "Pattern":
                node = MainFrame.getInstance().getTree().getSelectedNode().getNode();
                if(!(node instanceof Diagram)){
                    JOptionPane.showMessageDialog(null, "Only diagrams can be saved as patterns.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                if(!node.isChanged()){
                    JOptionPane.showMessageDialog(null, "Already saved.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                ApplicationFramework.getInstance().getSerializer().serializeToPattern((Diagram) node);
                break;
            case "Picture":
                node = MainFrame.getInstance().getTree().getSelectedNode().getNode();
                if(!(node instanceof Diagram)){
                    JOptionPane.showMessageDialog(null, "Only diagrams can be saved as pictures.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                if(!node.isChanged()){
                    JOptionPane.showMessageDialog(null, "Already saved.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                ApplicationFramework.getInstance().getSerializer().serializeToPicture((Diagram) node);
                break;
            case "Error":
                JOptionPane.showMessageDialog((Frame)null, "Please select one of the options.");
                break;
        }
    }

    private void showTypeDialog(){
        JDialog dialog = new JDialog((Frame) null, "Radio Dialog", true);

        JLabel label = new JLabel("How do you want to save your file?");

        JRadioButton option1 = new JRadioButton("Save project");
        JRadioButton option2 = new JRadioButton("Save pattern");
        JRadioButton option3 = new JRadioButton("Save picture");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(option1);
        buttonGroup.add(option2);
        buttonGroup.add(option3);

        JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
            if (option1.isSelected()) {
                option = "Project";
            } else if (option2.isSelected()) {
                option = "Pattern";
            } else if (option3.isSelected()){
                option = "Picture";
            } else{
                option = "Error";
            }
            dialog.dispose();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.add(label);
        panel.add(option1);
        panel.add(option2);
        panel.add(option3);
        panel.add(okButton);

        dialog.add(panel);
        dialog.pack();
        dialog.setVisible(true);
    }

    private File showFileDialog(String defaultName){
        File file = null;
        try {
            file = new File("projects/");
            if (!file.exists()) {
                file.mkdir();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        
        JFileChooser saveFileChooser = new JFileChooser(file);
        saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        saveFileChooser.setSelectedFile(new File(defaultName + ".json"));
        int returnValue = saveFileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = saveFileChooser.getSelectedFile();
            return selectedFile;
        } else {
            JOptionPane.showMessageDialog(null, "Select directory to save.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
