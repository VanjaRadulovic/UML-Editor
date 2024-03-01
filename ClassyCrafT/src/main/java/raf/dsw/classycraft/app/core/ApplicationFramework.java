package raf.dsw.classycraft.app.core;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.command.CommandManager;
import raf.dsw.classycraft.app.logger.Logger;
import raf.dsw.classycraft.app.serializer.Serializer;
import raf.dsw.classycraft.app.state.State;
import raf.dsw.classycraft.app.state.StateManager;

@Setter
@Getter
public class ApplicationFramework {

    protected Gui gui;
    protected Repository repository;
    protected MessageGenerator messageGenerator;
    protected Logger consoleLogger;
    protected Logger fileLogger;
    protected StateManager stateManager;
    protected Serializer serializer;
    protected CommandManager commandManager;

    public void run(){
        this.gui.start();
    }

    public void initialise(Gui gui, Repository repository,
                           MessageGenerator messageGenerator, Logger consoleLogger, Logger fileLogger, StateManager state, Serializer serializer, CommandManager commandManager)
    {
        this.gui = gui;
        this.repository = repository;
        this.messageGenerator = messageGenerator;
        this.consoleLogger = consoleLogger;
        this.fileLogger = fileLogger;
        this.stateManager = state;
        this.serializer = serializer;
        this.commandManager = commandManager;
    }


    // Singleton
    private static ApplicationFramework instance;

    private ApplicationFramework(){

    }

    public static ApplicationFramework getInstance(){
        if(instance==null){
            instance = new ApplicationFramework();
        }
        return instance;
    }

}
