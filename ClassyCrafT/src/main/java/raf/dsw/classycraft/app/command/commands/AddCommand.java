package raf.dsw.classycraft.app.command.commands;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.error.ErrorType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.views.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.views.painters.ElementPainter;
import raf.dsw.classycraft.app.repository.ElementType;
import raf.dsw.classycraft.app.repository.implementation.Diagram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@Getter
@Setter
public class AddCommand extends Command{
    private int option;
    private String param1;
    private String param2;

    public AddCommand(DiagramView diagramView, MouseEvent e) {
        super(diagramView, e);
    }

    @Override
    public void execute() {
        Diagram diagram = (Diagram) ApplicationFramework.getInstance().getRepository().findDiagramByName(null, diagramView.getName());
        MainFrame.getInstance().getTree().setSelectedNode(diagram);
        int type = showRadioPopup();
        if (type ==0)
            return;
        String[] params = showParamPopup();
        if(params[0].equals("ERROR"))
            return;
        switch (type){
            case 1:
                ApplicationFramework.getInstance().getRepository().addChild(diagram, params[0], params[1], e.getPoint(), 100, ElementType.CLASS);
                option = 1;
                param1 = params[0];
                param2 = params[1];
                break;
            case 2:
                ApplicationFramework.getInstance().getRepository().addChild(diagram, params[0], params[1], e.getPoint(), 100, ElementType.INTERFACE);
                option = 1;
                param1 = params[0];
                param2 = params[1];
                break;
            case 3:
                ApplicationFramework.getInstance().getRepository().addChild(diagram, params[0], params[1], e.getPoint(), 100, ElementType.ENUM);
                option = 1;
                param1 = params[0];
                param2 = params[1];
                break;
        }

        diagram.setChanged(true);
    }

    @Override
    public void undo() {
        for(ElementPainter painter : diagramView.getPainterList()){
            if(painter.getShape().contains(e.getX(), e.getY())){
                Command command = new DeleteCommand(diagramView, e);
                command.execute();
            }
        }
    }

    @Override
    public void redo() {
        Diagram diagram = (Diagram) ApplicationFramework.getInstance().getRepository().findDiagramByName(null, diagramView.getName());
        MainFrame.getInstance().getTree().setSelectedNode(diagram);
        switch (option){
            case 1:
                ApplicationFramework.getInstance().getRepository().addChild(diagram, param1, param2, e.getPoint(), 100, ElementType.CLASS);
                break;
            case 2:
                ApplicationFramework.getInstance().getRepository().addChild(diagram, param1, param2, e.getPoint(), 100, ElementType.INTERFACE);
                break;
            case 3:
                ApplicationFramework.getInstance().getRepository().addChild(diagram, param1, param2, e.getPoint(), 100, ElementType.ENUM);
                break;
        }
    }

    public int showRadioPopup() {
        // Create the dialog
        JDialog dialog = new JDialog((Frame) null, "Select Option", true);
        dialog.setLayout(new FlowLayout());

        // Create radio buttons
        JRadioButton classButton = new JRadioButton("Class");
        JRadioButton interfaceButton = new JRadioButton("Interface");
        JRadioButton enumButton = new JRadioButton("Enum");

        // Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(classButton);
        group.add(interfaceButton);
        group.add(enumButton);

        // Add radio buttons to dialog
        dialog.add(classButton);
        dialog.add(interfaceButton);
        dialog.add(enumButton);

        // Create a button to confirm the selection
        JButton confirmButton = new JButton("Confirm");
        dialog.add(confirmButton);

        // Array to hold the result
        final int[] result = new int[1];
        result[0] = 0;
        // Add action listener to the confirm button
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (classButton.isSelected()) {
                    result[0] = 1;
                } else if (interfaceButton.isSelected()) {
                    result[0] = 2;
                } else if (enumButton.isSelected()) {
                    result[0] = 3;
                }
                if(result[0]==0) {
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.NAME_CANNOT_BE_EMPTY);
                } else {
                    dialog.dispose();
                }
            }
        });

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                result[0] = 0;
            }
        });
        // Set dialog size and make it visible
        dialog.pack();
        dialog.setLocationRelativeTo(null); // Center on screen
        dialog.setVisible(true);

        // Return the result
        return result[0];
    }

    public String[] showParamPopup() {
        String[] params = new String[2];

        JDialog dialog = new JDialog((Frame) null, "Enter Data", true);
        dialog.setLayout(new GridLayout(3, 2));

        // Create text fields
        JTextField nameTextField = new JTextField(10);
        JTextField visibilityTextField = new JTextField(10);

        // Create and add components to dialog
        dialog.add(new JLabel("Enter element name:"));
        dialog.add(nameTextField);
        dialog.add(new JLabel("Enter element visibility:"));
        dialog.add(visibilityTextField);

        // Create and add the Done button
        JButton doneButton = new JButton("Done");
        dialog.add(doneButton);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                params[0] = nameTextField.getText().trim();
                params[1] = visibilityTextField.getText().trim();


                if(params[1].equals("") || params[0].equals("") ) {
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage(ErrorType.NAME_CANNOT_BE_EMPTY);
                } else {
                    dialog.dispose();
                }
            }
        });

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                params[0]="ERROR";
            }
        });

        // Set dialog size and make it visible
        dialog.pack();
        dialog.setLocationRelativeTo(null); // Center on screen
        dialog.setVisible(true);

        return params;
    }
}
