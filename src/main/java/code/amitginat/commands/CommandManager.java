package code.amitginat.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    List<AbstractCommand> commands;
    List<String> helps;
    List<String> prefixs;

    public CommandManager(AbstractCommand... commands) {
        this.commands = new ArrayList<>(commands.length + 5);
        this.helps = new ArrayList<>(commands.length + 5);
        this.prefixs = new ArrayList<>(commands.length + 5);
        this.commands.addAll(Arrays.asList(commands));
        setPrefixs();
        setHelps();

    }

    public void setEvents(MessageReceivedEvent event)
    {
        for (var command : commands) {
            command.setEvent(event);
        }
    }

    public List<AbstractCommand> getCommands() {
        return commands;
    }

    public List<String> getHelps() {
        return helps;
    }

    private void setHelps() {
        for (var command : commands) {
            helps.add(command.explain());
        }
    }

    private void setPrefixs() {
        for (var command : commands) {
            prefixs.add(command.prefix());
        }
    }

    public List<String> getPrefixs() {
        return prefixs;
    }
}
