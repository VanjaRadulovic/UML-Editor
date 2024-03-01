package raf.dsw.classycraft.app.serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.repository.composite.Node;
import raf.dsw.classycraft.app.repository.composite.NodeComposite;
import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SerializerImplementation implements Serializer{

    @Override
    public void serializeToJSON(Project project, File root) {
        ObjectMapper objectMapper = new ObjectMapper();
        project.setChanged(false);

        try {
            String baseDirectoryPath;

            if (root != null && !root.getAbsolutePath().contains(project.getName() + ".json")) {
                baseDirectoryPath = root.getAbsolutePath();
            } else {
                baseDirectoryPath = "projects";
            }

            File file = new File(baseDirectoryPath, project.getName() + ".json");

            File parentDirectory = file.getParentFile();
            if (!parentDirectory.exists()) {
                parentDirectory.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }
            objectMapper.writeValue(file, project);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serializeToPattern(Diagram diagram){
        diagram.setChanged(false);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File file = new File("patterns/" + diagram.getName() + ".json");

            File parentDirectory = file.getParentFile();
            if (!parentDirectory.exists()) {
                parentDirectory.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }
            objectMapper.writeValue(file, diagram);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializeToPicture(Diagram diagram){
        //TODO
    }

    @Override
    public void serializeToJAVA(Project project) {
        //TODO
    }

    @Override
    public void openSerialized(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Project project = mapper.readValue(file, Project.class);
            project.setParent(ApplicationFramework.getInstance().getRepository().getProjectExplorer());
            project.setSubscribers(new ArrayList<>());
            project.setChanged(false);
            ApplicationFramework.getInstance().getRepository().addChild(project);
            setChildren(project);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setChildren(NodeComposite parent){
        for(Node node : parent.getChildren()){
            //Dosta lose al it is what it is
            node.setParent(parent);
            node.setSubscribers(new ArrayList<>());
            node.setChanged(false);
            ApplicationFramework.getInstance().getRepository().addChild(node);
        }
        for(Node node : parent.getChildren()){
            if(node instanceof NodeComposite){
                if(!((NodeComposite) node).getChildren().isEmpty()){
                    setChildren((NodeComposite) node);
                }
            }
        }
    }
}
