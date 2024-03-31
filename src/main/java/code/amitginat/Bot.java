package code.amitginat;

import code.amitginat.commands.CommandManager;
import code.amitginat.commands.func.*;
import code.amitginat.commands.music.*;
import code.amitginat.commands.other.*;
import code.amitginat.events.ReadEvent;
import code.amitginat.other.IsraelTime;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class Bot {

    /*
        Main class for the Bot. this class is the one that gathers everything into one.
     */

    private static CommandManager commandManager; // static command manager. will be used by the read-event and the help-command
    private static JDA bot; // the bot itself
    public static JDABuilder createBot(){
        /*
            This function will:
                * build the bot (enable the right intents, set the activity and the read event listener)
                * create the command manager and add the commands to it which will be used by the read event
                *  enable the voice state cache flag and return it to main.
         */
        JDABuilder bot = JDABuilder.createDefault(ApiKeys.JDA_TOKEN);
        //INTENTS
        bot.enableIntents(
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES
        );
        //ACTIVITY
        bot.setActivity(Activity.listening("ginat help"));

        //COMMANDS
        commandManager = new CommandManager(

                new YnetCommand(),
                new RiddleCommand(),
                new JokeCommand(),
                new ChuckNorrisCommand(),
                new TimeCommand(),
                new TimeUntilCommand(),
                new DaysUntilCommand(),
                new HelpCommand(),
                new ClearMessageCommand(),
                new JoinCommand(),
                new LeaveCommand(),
                new PlayCommand(),
                new StopCommand(),
                new ResumeCommand(),
                new SkipCommand(),
                new NowPlayingCommand(),
                new WhatsNextCommand(),
                new ClearMusicCommand(),
                new WhoisCommand(),
                new FactCommand()
        );
        bot.enableCache(CacheFlag.VOICE_STATE);
        bot.addEventListeners(new ReadEvent());
        return bot;
    }
    public static JDA getBot(){
        return bot;
    }
    public static CommandManager getCommandManager(){
        return commandManager;
    }
    public static void main(String[] args) {
        try{
            bot = createBot().build(); // create the bot, build it. this will activate the bot and make it listen to the read event.
        }
        catch (Throwable throwable){
            System.out.println(IsraelTime.get() + " ");throwable.printStackTrace();

        }
    }

}
