package code.amitginat.other;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.*;

import java.awt.*;

public class GinatUtil {

    public static MessageEmbed getMessageEmbed(String title, String description, Field... fields){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title);

        builder.addBlankField(false);
        builder.setColor(Color.PINK);

        builder.setDescription(description);

        for (var field:fields) {
            builder.addField(field);
        }
        return builder.build();
    }
}
