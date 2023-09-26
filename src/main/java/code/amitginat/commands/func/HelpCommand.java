package code.amitginat.commands.func;

import code.amitginat.Bot;
import code.amitginat.commands.AbstractCommand;

public class HelpCommand extends AbstractCommand {
    @Override
    public void run() {
        StringBuilder builder = new StringBuilder();
        builder.append("------------------------------------------------------");
        builder.append(String.format("%n"));

        var allCommands = Bot.commandManager.getCommands();
        builder.append("**Music:**");
        for (var command : allCommands) {
            if (command.getType().equals("music")) {
                String thisPrefix = command.prefix();
                String thisHelp = command.explain();
                builder.append(String.format("%n*ginat %s - %s*",
                        thisPrefix,
                        thisHelp));
            }
        }
        builder.append(String.format("%n"));
        builder.append("**Func:**");
        for (var command : allCommands) {
            if (command.getType().equals("func")) {
                String thisPrefix = command.prefix();
                String thisHelp = command.explain();
                builder.append(String.format("%n*ginat %s - %s*",
                        thisPrefix,
                        thisHelp));
            }
        }
        builder.append("%n".formatted());
        builder.append("**Irrelevant:**");
        for (var command : allCommands) {
            if (command.getType().equals("other")) {
                String thisPrefix = command.prefix();
                String thisHelp = command.explain();
                builder.append(String.format("%n*ginat %s - %s*",
                        thisPrefix,
                        thisHelp));
            }
        }
        builder.append(String.format("%n"));
        builder.append("------------------------------------------------------");
        channel.sendMessage(builder).queue();

    }
    @Override
    public String getType() {
        return "func";
    }
    @Override
    public String prefix() {
        return "help";
    }

    @Override
    public String explain() {
        return "מציג הודעה המסבירה על כל פקודה";
    }
}
