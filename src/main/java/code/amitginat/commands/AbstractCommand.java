package code.amitginat.commands;

import code.amitginat.Bot;
import code.amitginat.music.lavaplayer.GuildMusicManager;
import code.amitginat.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public abstract class AbstractCommand {
    /*
        this is the abstract command class. it will be extended by every command in this project.
        this class will contain every resource each command should need to work.


     */



    protected MessageChannelUnion channel; // the channel the event was triggered on.
    protected String message;
    protected Member member, self;
    protected GuildVoiceState memberVoiceState, selfVoiceState;
    protected AudioManager audioManager;
    protected GuildMusicManager musicManager;
    public void setEvent(MessageReceivedEvent event){
        /*
            will be used by the command manager.
            for every single command:
               this method will update where the command is working on by the event that triggered the bot.
               this method will give every single command the resources it needs to work and chat with the user who triggers it.
         */
        channel = event.getChannel(); // the channel the bot will be talking in

        message = event.getMessage().getContentRaw(); // the message that triggered the command

        member = event.getMember(); // the member that triggered the command
        self = event.getGuild().getMember(Bot.getBot().getSelfUser()); // the user that bot is on


        selfVoiceState = self.getVoiceState(); // the bot's voice state
        memberVoiceState = member.getVoiceState(); // the member that triggered the command's voice state
        audioManager = event.getGuild().getAudioManager(); // the audio manager the server is using
        musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());

    }
    public abstract String getType(); // this function would be implemented by every command extending this class.
    public abstract void run(); // this function would be implemented by every command extending this class.

    public abstract String name(); // this function would be implemented by every command extending this class.

    public abstract String explain(); // this function would be implemented by every command extending this class.
}
