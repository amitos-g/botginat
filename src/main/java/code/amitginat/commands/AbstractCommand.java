package code.amitginat.commands;

import code.amitginat.Bot;
import code.amitginat.music.lavaplayer.GuildMusicManager;
import code.amitginat.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public abstract class AbstractCommand {
    protected MessageChannelUnion channel;
    protected Guild guild;
    protected String message;
    protected Member member;
    protected Member self;
    protected GuildVoiceState memberVoiceState;
    protected GuildVoiceState selfVoiceState;
    protected AudioManager audioManager;
    protected MessageReceivedEvent event;
    protected GuildMusicManager musicManager;
    protected Message messageFull;
    public void setEvent(MessageReceivedEvent event){
        this.event = event;
        channel = event.getChannel();
        guild = event.getGuild();
        messageFull = event.getMessage();
        message = event.getMessage().getContentRaw();
        member = event.getMember();
        self = guild.getMember(Bot.getBot().getSelfUser());
        assert self != null;
        selfVoiceState = self.getVoiceState();
        assert member != null;
        memberVoiceState = member.getVoiceState();
        audioManager = guild.getAudioManager();
        musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());

    }
    public abstract String getType();
    public abstract void run();

    public abstract String prefix();

    public abstract String explain();
}
