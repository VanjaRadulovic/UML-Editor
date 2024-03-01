package raf.dsw.classycraft.app.logger;

import raf.dsw.classycraft.app.logger.impl.ConsoleLogger;
import raf.dsw.classycraft.app.logger.impl.FileLogger;

public class LoggerFactory {
    private LoggerFactory(){};
    public static Logger createConsoleLogger(){
        return new ConsoleLogger();
    }

    public static Logger createFileLogger(){
        return new FileLogger();
    }
}
