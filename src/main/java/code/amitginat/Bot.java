package code.amitginat;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Bot {


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


        //CACHE

        bot.enableCache(CacheFlag.VOICE_STATE);


        bot.addEventListeners(new ReadEvent());

        return bot;
    }


    public static void main(String[] args) throws LoginException {

        bot = createBot().build();
    }
    public static JDA getBot(){
        return bot;
    }
}
