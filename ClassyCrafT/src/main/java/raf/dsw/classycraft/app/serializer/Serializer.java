package raf.dsw.classycraft.app.serializer;

import raf.dsw.classycraft.app.repository.implementation.Diagram;
import raf.dsw.classycraft.app.repository.implementation.Project;

import java.io.File;

public interface Serializer {
     void serializeToJSON(Project project, File file);
     void serializeToPattern(Diagram diagram);
     void serializeToPicture(Diagram diagram);
     void serializeToJAVA(Project project);
     void openSerialized(File file);
}
