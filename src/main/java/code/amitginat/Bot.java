package code.amitginat;

import code.amitginat.commands.CommandManager;
import code.amitginat.commands.func.ClearMessageCommand;
import code.amitginat.commands.func.HelpCommand;
import code.amitginat.commands.music.*;
import code.amitginat.events.ReadEvent;
import code.amitginat.other.IsraelTime;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Bot {


    public static CommandManager commandManager;
    private static JDA bot;
    public static JDABuilder createBot(){

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
                new ClearMusicCommand()

        );


        //CACHE

        bot.enableCache(CacheFlag.VOICE_STATE);
        bot.addEventListeners(new ReadEvent());
        return bot;
    }


    public static void main(String[] args) throws LoginException {
        try{
            bot = createBot().build();
        }
        catch (Throwable throwable){
            bot = createBot().build();
            System.out.println(IsraelTime.get() + " ");throwable.printStackTrace();

        }
    }
    public static JDA getBot(){
        return bot;
    }
}
