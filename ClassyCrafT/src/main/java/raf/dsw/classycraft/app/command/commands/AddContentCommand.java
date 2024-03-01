package raf.dsw.classycraft.app.command.commands;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Attribute;
import raf.dsw.classycraft.app.repository.implementation.elements.content.Method;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Enumeracija;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Interfejs;
import raf.dsw.classycraft.app.repository.implementation.elements.interclass.Klasa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AddContentCommand extends Command{
    private List<Method> classMethods = new ArrayList<>();
    private List<Attribute> classAttributes = new ArrayList<>();
    private List<Method> interfaceMethods = new ArrayList<>();
    private List<Attribute> enumAttributes = new ArrayList<>();
    private JPanel attributesPanel;
    private JPanel methodsPanel;
    private JDialog classPopup;
    private JDialog enumPopup;
    private JDialog interfacePopup;
    private String name;
    private String visibility;

    private int type;
    private List<Method> addedMethods;
    private List<Attribute> addedAttributes;
    private Node node;

    public AddContentCommand(DiagramView diagramView, MouseEvent e) {
        super(diagramView, e);
        addedMethods = new ArrayList<>();
        addedAttributes = new ArrayList<>();
    }

    @Override
    public void execute() {
        Diagram diagram = (Diagram) ApplicationFramework.getInstance().getRepository().findDiagramByName(null, diagramView.getName());
        List<ElementPainter> painterList = diagramView.getPainterList();

        for(ElementPainter painter: painterList){
            if(painter.getShape().contains(e.getX(),e.getY())){
                node = diagram.getChildByName(painter.getElement().getName());
                name = painter.getElement().getName();
                if(node instanceof Klasa){
                    type = 1;
                    visibility = ((Klasa) painter.getElement()).getVisibility();
                    classAttributes.addAll(((Klasa) node).getAttributes());
                    classMethods.addAll(((Klasa) node).getMethods());
                    showClassPopup();
                    for(Method m : classMethods){
                        if(!((Klasa) node).getMethods().contains(m)){
                            addedMethods.add(m);
                        }
                    }
                    for(Attribute a : classAttributes){
                        if(!((Klasa) node).getAttributes().contains(a)){
                            addedAttributes.add(a);
                        }
                    }
                    ApplicationFramework.getInstance().getRepository().addContent(node, classAttributes, classMethods,name, visibility);
                }
                if(node instanceof Interfejs){
                    type = 2;
                    visibility = ((Interfejs) painter.getElement()).getVisibility();
                    interfaceMethods.addAll(((Interfejs) node).getMethods());
                    showInterfacePopup();
                    for(Method m : interfaceMethods){
                        if(!((Interfejs) node).getMethods().contains(m)){
                            addedMethods.add(m);
                        }
                    }
                    ApplicationFramework.getInstance().getRepository().addContent(node, null , interfaceMethods,name, visibility);
                }
                if(node instanceof Enumeracija){
                    type = 3;
                    visibility = ((Enumeracija) painter.getElement()).getVisibility();
                    enumAttributes.addAll(((Enumeracija) node).getAttributes());
                    showEnumPopup();
                    for(Attribute a : enumAttributes){
                        if(!((Enumeracija) node).getAttributes().contains(a)){
                            addedAttributes.add(a);
                        }
                    }
                    ApplicationFramework.getInstance().getRepository().addContent(node, enumAttributes , null,name, visibility);
                }
            }
        }

        diagram.setChanged(true);
    }

    @Override
    public void undo() {
        switch (type){
            case 1:
                List<Method> tempMethC = new ArrayList<>(classMethods);
                tempMethC.removeAll(addedMethods);
                List<Attribute> tempAttrC = new ArrayList<>(classAttributes);
                tempAttrC.removeAll(addedAttributes);
                ApplicationFramework.getInstance().getRepository().addContent(node, tempAttrC , tempMethC,name, visibility);
                break;
            case 2:
                List<Method> tempMethI = new ArrayList<>(interfaceMethods);
                tempMethI.removeAll(addedMethods);
                ApplicationFramework.getInstance().getRepository().addContent(node, null , tempMethI,name, visibility);
                break;
            case 3:
                List<Attribute> tempAttrE = new ArrayList<>(enumAttributes);
                tempAttrE.removeAll(addedAttributes);
                ApplicationFramework.getInstance().getRepository().addContent(node, tempAttrE , null,name, visibility);
                break;
        }
    }

    @Override
    public void redo() {
        switch (type){
            case 1:
                ApplicationFramework.getInstance().getRepository().addContent(node, classAttributes , classMethods,name, visibility);
                break;
            case 2:
                ApplicationFramework.getInstance().getRepository().addContent(node, null , interfaceMethods,name, visibility);
                break;
            case 3:
                ApplicationFramework.getInstance().getRepository().addContent(node, enumAttributes , null,name, visibility);
                break;
        }
    }



    private void showClassPopup() {
        classPopup = new JDialog((Frame) null, "Class Popup", true);
        classPopup.setLocationRelativeTo(null);
        classPopup.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel panelsRow = new JPanel();
        panelsRow.setLayout(new BoxLayout(panelsRow, BoxLayout.X_AXIS));

        methodsPanel = new JPanel();
        createColumnPanel(methodsPanel, "Methods", null, classMethods, "CLASS");
        panelsRow.add(methodsPanel);

        attributesPanel = new JPanel();
        createColumnPanel(attributesPanel, "Attributes", classAttributes, null, "CLASS");
        panelsRow.add(attributesPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        classPopup.add(panelsRow, gbc);

        // Second Row: Name Text Fields and Update Buttons
        JTextField nameFieldName = new JTextField(name, 10);
        JButton updateButtonName = new JButton("Update");

        JTextField nameFieldVisability = new JTextField(visibility, 10);
        JButton updateButtonVisability = new JButton("Update");

        JPanel secondRow = new JPanel();
        secondRow.add(nameFieldName);
        secondRow.add(updateButtonName);
        secondRow.add(nameFieldVisability);
        secondRow.add(updateButtonVisability);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        classPopup.add(secondRow, gbc);

        updateButtonName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newItemName = nameFieldName.getText().trim();
                name = newItemName;
            }
        });

        updateButtonVisability.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newItemName = nameFieldVisability.getText().trim();
                visibility = newItemName;
            }
        });

        // Third Row: Done Button
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classPopup.dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        classPopup.add(doneButton, gbc);

        classPopup.setSize(1000, 300);
        classPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        classPopup.setVisible(true);
    }

    private void createColumnPanel(JPanel columnPanel, String content, java.util.List<Attribute> itemList, java.util.List<Method> itemList2, String type) {
        columnPanel.setBorder(BorderFactory.createTitledBorder(content));
        columnPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        if(itemList!=null){
            int row = 0;

            for (Attribute item : itemList) {
                JLabel nameLabel = new JLabel(item.getName());
                JTextField nameField = new JTextField(item.getName(), 15);

                JButton deleteButton = new JButton("Delete");

                JButton updateButton = new JButton("Update");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(type.equals("CLASS"))
                            classAttributes.remove(item);
                        else
                            enumAttributes.remove(item);
                        nameField.setText("");
                        updateColumnPanel(columnPanel,content, itemList,null, type);
                    }
                });

                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newItemName = nameField.getText().trim();

                        if(type.equals("CLASS")){
                            classAttributes.remove(item);
                            classAttributes.add(new Attribute(newItemName));
                        }
                        else
                        {
                            enumAttributes.remove(item);
                            enumAttributes.add(new Attribute(newItemName));
                        }
                        nameField.setText("");
                        updateColumnPanel(columnPanel,content,itemList,null,type);
                    }
                });

                gbc.gridx = 0;
                gbc.gridy = row;
                columnPanel.add(nameLabel, gbc);

                gbc.gridx = 1;
                gbc.gridy = row;
                columnPanel.add(nameField, gbc);

                gbc.gridx = 2;
                gbc.gridy = row;
                columnPanel.add(deleteButton, gbc);

                gbc.gridx = 3;
                gbc.gridy = row;
                columnPanel.add(updateButton, gbc);

                row++;
            }

            JTextField newItemField = new JTextField(15);
            JButton addItemButton = new JButton("Add " +content.substring(0, content.length() - 1) + ":");
            addItemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newItemName = newItemField.getText().trim();
                    if (!newItemName.isEmpty()) {
                        if(type.equals("CLASS"))
                            classAttributes.add(new Attribute(newItemName));
                        else
                            enumAttributes.add(new Attribute(newItemName));

                        updateColumnPanel(columnPanel,content,itemList,null,type);
                        newItemField.setText("");
                    }
                }
            });

            gbc.gridx = 0;
            gbc.gridy = row;
            columnPanel.add(new JLabel("Add " +content.substring(0, content.length() - 1) + ":"), gbc);

            gbc.gridx = 1;
            gbc.gridy = row;
            columnPanel.add(newItemField, gbc);

            gbc.gridx = 2;
            gbc.gridy = row;
            columnPanel.add(addItemButton, gbc);
        }

        else{

            int row = 0;
            for (Method item : itemList2) {
                JLabel nameLabel = new JLabel(item.getName());
                JTextField nameField = new JTextField(item.getName(), 15);

                JButton deleteButton = new JButton("Delete");

                JButton updateButton = new JButton("Update");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(type.equals("CLASS")){
                            classMethods.remove(item);
                        }
                        else
                            interfaceMethods.remove(item);
                        nameField.setText("");
                        updateColumnPanel(columnPanel,content, null,itemList2,type);
                    }
                });

                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newItemName = nameField.getText().trim();
                        if(type.equals("CLASS")){
                            classMethods.remove(item);
                            classMethods.add(new Method(newItemName));
                        }
                        else{
                            interfaceMethods.remove(item);
                            interfaceMethods.add(new Method(newItemName));
                        }
                        nameField.setText("");
                        updateColumnPanel(columnPanel,content,null,itemList2,type);
                    }
                });

                gbc.gridx = 0;
                gbc.gridy = row;
                columnPanel.add(nameLabel, gbc);

                gbc.gridx = 1;
                gbc.gridy = row;
                columnPanel.add(nameField, gbc);

                gbc.gridx = 2;
                gbc.gridy = row;
                columnPanel.add(deleteButton, gbc);

                gbc.gridx = 3;
                gbc.gridy = row;
                columnPanel.add(updateButton, gbc);

                row++;
            }

            JTextField newItemField = new JTextField(15);
            JButton addItemButton = new JButton("Add " +content.substring(0, content.length() - 1) + ":");
            addItemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newItemName = newItemField.getText().trim();
                    if (!newItemName.isEmpty()) {
                        if(type.equals("CLASS")){
                            classMethods.add(new Method(newItemName));
                        }
                        else
                            interfaceMethods.add(new Method(newItemName));
                        updateColumnPanel(columnPanel,content,null,itemList2,type);
                        newItemField.setText("");
                    }
                }
            });

            gbc.gridx = 0;
            gbc.gridy = row;
            columnPanel.add(new JLabel("Add " +content.substring(0, content.length() - 1) + ":"), gbc);

            gbc.gridx = 1;
            gbc.gridy = row;
            columnPanel.add(newItemField, gbc);

            gbc.gridx = 2;
            gbc.gridy = row;
            columnPanel.add(addItemButton, gbc);

        }

    }



    private void updateColumnPanel(JPanel panel, String name, java.util.List<Attribute> itemList, List<Method> itemList2, String type) {
        panel.removeAll();
        createColumnPanel(panel,name,itemList,itemList2,type);
        panel.revalidate();
        panel.repaint();
    }




    private void showEnumPopup() {
        enumPopup = new JDialog((Frame) null, "Enum Popup", true);
        enumPopup.setLocationRelativeTo(null);
        enumPopup.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // First Row: Attribute Panel
        attributesPanel = new JPanel();
        createColumnPanel(attributesPanel, "Methods", enumAttributes, null, "JAVA SWING CRKO DA BOG DA");

        gbc.gridx = 0;
        gbc.gridy = 0;
        enumPopup.add(attributesPanel, gbc);

        // Second Row: Name Text Fields and Update Buttons
        JTextField nameFieldName = new JTextField(name, 10);
        JButton updateButtonName = new JButton("Update");

        JTextField nameFieldVisability = new JTextField(visibility, 10);
        JButton updateButtonVisability = new JButton("Update");

        JPanel secondRow = new JPanel();
        secondRow.add(nameFieldName);
        secondRow.add(updateButtonName);
        secondRow.add(nameFieldVisability);
        secondRow.add(updateButtonVisability);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        enumPopup.add(secondRow, gbc);

        updateButtonName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newItemName = nameFieldName.getText().trim();
                name = newItemName;
            }
        });

        updateButtonVisability.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newItemName = nameFieldVisability.getText().trim();
                visibility = newItemName;
            }
        });

        // Third Row: Done Button
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enumPopup.dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        enumPopup.add(doneButton, gbc);

        enumPopup.setSize(1000, 300);
        enumPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        enumPopup.setVisible(true);
    }

    private void showInterfacePopup() {
        interfacePopup = new JDialog((Frame) null, "Interface Popup", true);
        interfacePopup.setLocationRelativeTo(null);
        interfacePopup.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // First Row: Method Panel
        methodsPanel = new JPanel();
        createColumnPanel(methodsPanel, "Methods", null, interfaceMethods, "JAVA SWING CRKO DA BOG DA");
        gbc.gridx = 0;
        gbc.gridy = 0;
        interfacePopup.add(methodsPanel, gbc);

        // Second Row: Name Text Fields and Update Buttons
        JTextField nameFieldName = new JTextField(name, 10);
        JButton updateButtonName = new JButton("Update");

        JTextField nameFieldVisability = new JTextField(visibility, 10);
        JButton updateButtonVisability = new JButton("Update");

        JPanel secondRow = new JPanel();
        secondRow.add(nameFieldName);
        secondRow.add(updateButtonName);
        secondRow.add(nameFieldVisability);
        secondRow.add(updateButtonVisability);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        interfacePopup.add(secondRow, gbc);

        updateButtonName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newItemName = nameFieldName.getText().trim();
                name = newItemName;
            }
        });

        updateButtonVisability.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newItemName = nameFieldVisability.getText().trim();
                visibility = newItemName;
            }
        });

        // Third Row: Done Button
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfacePopup.dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        interfacePopup.add(doneButton, gbc);

        interfacePopup.setSize(1000, 300);
        interfacePopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        interfacePopup.setVisible(true);
    }
}
