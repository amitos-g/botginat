package code.amitginat.commands.discord;

import code.amitginat.commands.AbstractCommand;
import code.amitginat.util.GinatUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.List;

public class DeleteChannelCommand extends AbstractCommand {
    @Override
    public void run() {
        var msgArr = message.split(" ");
        if(msgArr.length < 3){
            GinatUtil.sendEmbed(channel, "Error", new MessageEmbed.Field("missing channel name", "", false));
            return;
        }
        String channelName = msgArr[2];
        Guild g = member.getGuild();
        try{
            List<TextChannel> channels = g.getTextChannelsByName(channelName, true);
            if(channels.isEmpty()){
                GinatUtil.sendEmbed(channel, "Error", new MessageEmbed.Field("channel not found", "", false));
                return;
            }
            TextChannel channel = channels.get(0);
            channel.delete().reason("member %s wanted to".formatted(member.getUser().getName())).queue();
        }
        catch (Exception e){

        }
    }
    @Override
    public String getType() {
        return "discord";
    }
    @Override
    public String name() {
        return "delete-channel";
    }

    @Override
    public String explain() {
        return "deletes a channel: ginat delete channel [name]";
    }
}
