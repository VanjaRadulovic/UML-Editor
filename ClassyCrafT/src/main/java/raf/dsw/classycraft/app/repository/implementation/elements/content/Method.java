package raf.dsw.classycraft.app.repository.implementation.elements.content;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Method extends ClassContent{

    private String type;
    private String returnType;

    public Method(String name) {
        super(name);
    }
}
