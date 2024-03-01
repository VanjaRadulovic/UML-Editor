package raf.dsw.classycraft.app.repository.implementation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;

@Getter
@Setter
@NoArgsConstructor
public class Project extends NodeComposite{
    private String author;
    @Setter
    private String path;
    public Project(String name, Node parent,String author, String path) {
        super(name, parent, "project");
        this.author = author;
        this.path = path;
    }


    @Override
    public void addChild(Node child) {
        if (child != null &&  child instanceof Package){
            Package pack = (Package) child;
            if (!this.getChildren().contains(pack)){
                this.getChildren().add(pack);
            }
        }
    }

    @Override
    public void removeChildren(Node child) {
        if (child instanceof Package && this.getChildren().contains((Package)child)){
            this.getChildren().remove(child);
            child.notifySubscribers("DELETE", null);
        }
    }

    public void changeAuthor(String author) {
        this.author = author;
        this.notifySubscribers("Author:" + author, null);
    }
}
