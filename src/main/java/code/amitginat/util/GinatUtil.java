package code.amitginat.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.awt.*;
import java.util.HashMap;

public class GinatUtil {


    public static void sendFullEmbed(MessageChannelUnion channel, String author, String title, MessageEmbed.Field... fields){
        channel.sendMessageEmbeds(buildEmbed(author, title, fields)).queue();
    }
    public static void sendEmbed(MessageChannelUnion channel, String title, MessageEmbed.Field... fields)
    {
        sendFullEmbed(channel, "botginat", title, fields);
    }
    public static MessageEmbed buildEmbed(String author, String title,  MessageEmbed.Field... fields){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title)
                .setColor(new Color(95, 77, 94))
                .setAuthor(author, null, "https://i.redd.it/94j0h823jdnb1.jpg")
                .setFooter("made by ginat | @amitos1");
        if (fields != null){
            for (MessageEmbed.Field field:
                    fields) {
                builder.addField(field);
            }
        }
        return builder.build();
    }
}
