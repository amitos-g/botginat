package code.amitginat.commands.discord;

import code.amitginat.commands.AbstractCommand;
import code.amitginat.util.GinatUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class CreateNewChannelCommand extends AbstractCommand {
    @Override
    public void run() {
        var msgArr = message.split(" ");
        if(msgArr.length < 3){
            GinatUtil.sendEmbed(channel, "Error", new MessageEmbed.Field("missing channel name", "", false));
            return;
        }
        String channelName = msgArr[2];
        Guild g = member.getGuild();
        g.createTextChannel(channelName).queue();
    }
    @Override
    public String getType() {
        return "discord";
    }
    @Override
    public String name() {
        return "create-channel";
    }

    @Override
    public String explain() {
        return "creates a new channel: ginat create channel [name]";
    }
}
