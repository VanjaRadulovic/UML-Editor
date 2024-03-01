package raf.dsw.classycraft.app.gui.swing.view.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import raf.dsw.classycraft.app.state.StateManager;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;

@Getter
@NoArgsConstructor
public class ProjectView extends JLabel implements ISubscriber {
    private String author;
    private String name;
    private StateManager stateManager;

    public ProjectView(String text){
        super(text);
        stateManager = new StateManager();
    }

    public void setAuthor(String author) {
        this.author = author;
        this.setText("<html>Ime: " + this.getName() + "<br>Autor: " + author+ "</html>");
    }

    @Override
    public void setName(String name) {
        this.name = name;
        this.setText("<html>Ime: " + name + "<br>Autor: " + this.author+ "</html>");
    }

    @Override
    public void update(Object obj, Enum e) {
        String text = (String) obj;
        if(text.contains("Author:")){
            setAuthor(text.replace("Author:",""));
        }else{
            setName(text.replace("Name:",""));
        }
    }
}
