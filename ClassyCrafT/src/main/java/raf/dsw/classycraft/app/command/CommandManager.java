package raf.dsw.classycraft.app.command;

import raf.dsw.classycraft.app.command.commands.Command;

public interface CommandManager {
    void execute(Command command);
    void undo();
    void redo();
}
