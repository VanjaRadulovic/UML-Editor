package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class OpenAction extends AbstractRudokAction{

    public OpenAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/open.png"));
        putValue(NAME, "Open");
        putValue(SHORT_DESCRIPTION, "Open");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = showFilePopup();
        if(file == null) return;
        ApplicationFramework.getInstance().getSerializer().openSerialized(file);
    }

    private File showFilePopup(){
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
        saveFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        int returnValue = saveFileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = saveFileChooser.getSelectedFile();
            return selectedFile;
        } else {
            JOptionPane.showMessageDialog(null, "Select directory to open.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
