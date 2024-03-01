package raf.dsw.classycraft.app.gui.swing.controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AboutAction extends AbstractRudokAction{

    public AboutAction() {
        putValue(NAME, "About Us");
        putValue(SHORT_DESCRIPTION, "About Us");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ImageIcon icon1 = new ImageIcon("ClassyCrafT/src/main/resources/images/vanja1.png");
        if (icon1.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Failed to load first image");
        }
        JLabel label1 = new JLabel("Vanja Radulovic 112/2020RN", icon1, JLabel.CENTER);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setVerticalTextPosition(JLabel.BOTTOM);

        ImageIcon icon2 = new ImageIcon("ClassyCrafT/src/main/resources/images/baki1.jpg");
        Image image = icon2.getImage();
        Image resizedImage = image.getScaledInstance(icon1.getImage().getWidth(null), icon1.getImage().getHeight(null), Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(resizedImage);
        JLabel label2 = new JLabel("Branislav Dragicevic 57/2019RN", icon2, JLabel.CENTER);
        label2.setHorizontalTextPosition(JLabel.CENTER);
        label2.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2)); // 1 row, 2 columns
        panel.add(label1);
        panel.add(label2);

        // Show the custom option pane
        JOptionPane.showMessageDialog(null, panel, "About", JOptionPane.PLAIN_MESSAGE);
    }
}