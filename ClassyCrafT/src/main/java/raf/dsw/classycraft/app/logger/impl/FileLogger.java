package raf.dsw.classycraft.app.logger.impl;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.error.messageGenerator.Message;
import raf.dsw.classycraft.app.logger.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class FileLogger implements Logger {
    private File file;

    public FileLogger() {
        this.file = new File("ClassyCrafT/src/main/resources/log.txt");

        try {
            if (file.createNewFile()) {
                System.out.println("File created.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("Error creating the file.");
        }
    }

    @Override
    public void log(Message message) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
            String formattedDate = dateFormat.format(new Date());

            FileWriter writer = new FileWriter(file, true);
            writer.write("[" + message.getErrorType() +"][" + formattedDate + "] " + message.getMessage() + "\n");
            writer.close();

        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    @Override
    public void update(Object obj, Enum e) {
        log((Message) obj);
    }
}
