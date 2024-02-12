package code.amitginat.commands.func;

import code.amitginat.Bot;
import code.amitginat.commands.AbstractCommand;

import java.util.HashMap;
import java.util.List;

public class HelpCommand extends AbstractCommand {
    @Override
    public void run() {
        StringBuilder builder = new StringBuilder();
        builder.append("------------------------------------------------------");
        builder.append(String.format("%n"));

        HashMap<String, List<AbstractCommand>> helpMap = Bot.getCommandManager().getHelpMap();

        builder.append("**Music:**");
        for(AbstractCommand command : helpMap.get("music")){
            String thisName = command.name();
            String thisHelp = command.explain();
            builder.append(String.format("%n*ginat %s - %s*",
                    thisName,
                    thisHelp));
        }
        builder.append(String.format("%n"));
        builder.append("**Func:**");
        for(AbstractCommand command : helpMap.get("func")){
            String thisPrefix = command.name();
            String thisHelp = command.explain();
            builder.append(String.format("%n*ginat %s - %s*",
                    thisPrefix,
                    thisHelp));
        }
        builder.append("%n".formatted());
        builder.append("**Irrelevant:**");
        for(AbstractCommand command : helpMap.get("other")){
            String thisPrefix = command.name();
            String thisHelp = command.explain();
            builder.append(String.format("%n*ginat %s - %s*",
                    thisPrefix,
                    thisHelp));
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
    public String name() {
        return "help";
    }

    @Override
    public String explain() {
        return "מציג הודעה המסבירה על כל פקודה";
    }
}
