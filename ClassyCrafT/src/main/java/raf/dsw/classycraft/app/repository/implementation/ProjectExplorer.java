package raf.dsw.classycraft.app.repository.implementation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;

@Getter
@Setter
@NoArgsConstructor
public class ProjectExplorer extends NodeComposite{


    public ProjectExplorer(String name) {
        super(name, null, "projectexplorer");
    }

    @Override
    public void addChild(Node child) {
        if (child instanceof Project){
            Project project = (Project) child;
            if (!this.getChildren().contains(project)){
                this.getChildren().add(project);
            }
        }
    }

    @Override
    public void removeChildren(Node child) {
        if (child instanceof Project && this.getChildren().contains((Project)child)){
            this.getChildren().remove(child);
        }
    }
}
