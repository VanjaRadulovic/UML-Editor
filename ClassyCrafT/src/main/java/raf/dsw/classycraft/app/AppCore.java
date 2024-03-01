package raf.dsw.classycraft.app;

import raf.dsw.classycraft.app.command.CommandManager;
import raf.dsw.classycraft.app.command.CommandManagerImplementation;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.core.Gui;
import raf.dsw.classycraft.app.core.MessageGenerator;
import raf.dsw.classycraft.app.core.Repository;
import raf.dsw.classycraft.app.error.messageGenerator.MessageGeneratorImplementation;
import raf.dsw.classycraft.app.gui.swing.SwingGui;
import raf.dsw.classycraft.app.logger.Logger;
import raf.dsw.classycraft.app.logger.LoggerFactory;
import raf.dsw.classycraft.app.repository.RepositoryImpl;
import raf.dsw.classycraft.app.serializer.Serializer;
import raf.dsw.classycraft.app.serializer.SerializerImplementation;
import raf.dsw.classycraft.app.state.StateManager;


public class AppCore {

    public static void main(String[] args) {
        ApplicationFramework appCore = ApplicationFramework.getInstance();
        Gui gui = new SwingGui();
        Repository repository = new RepositoryImpl();
        MessageGenerator messageGenerator = new MessageGeneratorImplementation();
        StateManager state = new StateManager();
        Logger consoleLogger = LoggerFactory.createConsoleLogger();
        Logger fileLogger = LoggerFactory.createFileLogger();
        messageGenerator.addSubscriber(consoleLogger);
        messageGenerator.addSubscriber(fileLogger);
        Serializer serializer = new SerializerImplementation();
        CommandManager commandManager = new CommandManagerImplementation();
        appCore.initialise(gui, repository, messageGenerator, consoleLogger, fileLogger, state, serializer, commandManager);
        appCore.run();
    }




}
