package code.amitginat.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager {
    HashMap<String, AbstractCommand> commandMap; // build like (command name : command)
    HashMap<String, List<AbstractCommand>> helpMap; // Built like (command type : list of relevant commands)
    public CommandManager(AbstractCommand... commands) {

        commandMap = new HashMap<>();
        helpMap = new HashMap<>();

        for (AbstractCommand command: commands) {
            commandMap.put(command.name(), command);

            List<AbstractCommand> current = helpMap.getOrDefault(command.getType(), new ArrayList<>());
            current.add(command);
            helpMap.put(command.getType(), current);
        }
    }

    public void setEvents(MessageReceivedEvent event)
    {
        for (AbstractCommand command: commandMap.values()) {
            command.setEvent(event);
        }
    }
    public HashMap<String, List<AbstractCommand>> getHelpMap() {
        return helpMap;
    }
    public AbstractCommand getCommand(String name){
        if(commandMap.containsKey(name)){
            return commandMap.get(name);
        }
        return null;
    }
}
