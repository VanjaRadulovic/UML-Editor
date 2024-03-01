package raf.dsw.classycraft.app.error.messageGenerator;


import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.error.ErrorType;

@Getter
@Setter
public class Message {
    private String message;
    private ErrorType errorType;

    public Message(String message, ErrorType errorType) {
        this.message = message;
        this.errorType = errorType;
    }
}