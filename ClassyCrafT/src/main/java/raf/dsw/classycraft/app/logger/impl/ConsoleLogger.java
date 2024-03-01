package raf.dsw.classycraft.app.logger.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.classycraft.app.error.messageGenerator.Message;
import raf.dsw.classycraft.app.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ConsoleLogger implements Logger {
    @Override
    public void log(Message message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
        String formattedDate = dateFormat.format(new Date());

        System.out.println("[" + message.getErrorType() + "][" + formattedDate + "]" + message.getMessage());
    }

    @Override
    public void update(Object obj, Enum e) {
        log((Message) obj);
    }
}
