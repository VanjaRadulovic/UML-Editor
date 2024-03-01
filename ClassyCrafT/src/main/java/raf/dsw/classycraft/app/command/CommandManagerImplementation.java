package raf.dsw.classycraft.app.command;

import raf.dsw.classycraft.app.command.commands.Command;

import java.util.List;
import java.util.Stack;

public class CommandManagerImplementation implements CommandManager{
    private List<Command> commands;
    private int index;

    public CommandManagerImplementation() {
        commands = new Stack<>();
        index = 0;
    }

    @Override
    public void execute(Command command) {
        command.execute();
        commands.add(command);
        index += 1;
    }

    @Override
    public void undo() {
        if(index > 0) {
            Command command = commands.get(index - 1);
            command.undo();
            index -= 1;
        }
    }

    @Override
    public void redo(){
        if(index  < commands.size()){
            Command command = commands.get(index);
            command.redo();
            index += 1;
        }
    }
}
