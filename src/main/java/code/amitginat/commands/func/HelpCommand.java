package code.amitginat.commands.func;

import code.amitginat.Bot;
import code.amitginat.commands.AbstractCommand;
import code.amitginat.util.GinatUtil;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.HashMap;
import java.util.List;

public class HelpCommand extends AbstractCommand {
    private HashMap<String, List<AbstractCommand>> helpMap;
    @Override
    public void run() {
        String title = "Get Some Help.";
        helpMap = Bot.getCommandManager().getHelpMap();
        GinatUtil.sendEmbed(channel, title, functions(), discord(), game(), musics(), others());

    }

    private MessageEmbed.Field game(){
        String title = "**Games:**";
        StringBuilder commands = new StringBuilder();
        for(AbstractCommand command : helpMap.get("game")){
            String thisPrefix = command.name();
            String thisHelp = command.explain();
            commands.append(String.format("%n*ginat %s - %s*",
                    thisPrefix,
                    thisHelp));
        }
        return new MessageEmbed.Field(title, commands.toString(), false);
    }


    private MessageEmbed.Field discord(){
        String title = "**Discord:**";
        StringBuilder commands = new StringBuilder();
        for(AbstractCommand command : helpMap.get("discord")){
            String thisPrefix = command.name();
            String thisHelp = command.explain();
            commands.append(String.format("%n*ginat %s - %s*",
                    thisPrefix,
                    thisHelp));
        }
        return new MessageEmbed.Field(title, commands.toString(), false);
    }
    private MessageEmbed.Field others(){
        String title = "**Irrelevant:**";
        StringBuilder commands = new StringBuilder();
        for(AbstractCommand command : helpMap.get("other")){
            String thisPrefix = command.name();
            String thisHelp = command.explain();
            commands.append(String.format("%n*ginat %s - %s*",
                    thisPrefix,
                    thisHelp));
        }
        return new MessageEmbed.Field(title, commands.toString(), false);
    }
    private MessageEmbed.Field musics(){
        String title = "**Music:**";
        StringBuilder commands = new StringBuilder();
        for(AbstractCommand command : helpMap.get("music")){
            String thisPrefix = command.name();
            String thisHelp = command.explain();
            commands.append(String.format("%n*ginat %s - %s*",
                    thisPrefix,
                    thisHelp));
        }
        return new MessageEmbed.Field(title, commands.toString(), false);
    }

    private MessageEmbed.Field functions(){
        String title = "**Func:**";
        StringBuilder commands = new StringBuilder();
        for(AbstractCommand command : helpMap.get("func")){
            String thisPrefix = command.name();
            String thisHelp = command.explain();
            commands.append(String.format("%n*ginat %s - %s*",
                    thisPrefix,
                    thisHelp));
        }
        return new MessageEmbed.Field(title, commands.toString(), false);
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
