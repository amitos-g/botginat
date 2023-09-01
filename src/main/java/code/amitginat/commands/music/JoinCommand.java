package code.amitginat.commands.music;

import code.amitginat.commands.AbstractCommand;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;

public class JoinCommand extends AbstractCommand {




    @Override
    public void run() {
        try {
            if (selfVoiceState.inAudioChannel()) {
                channel.sendMessage("אני בשיחה כבר").queue();
                return;
            }
            if (!memberVoiceState.inAudioChannel()) {
                channel.sendMessage("אתה לא בשיחה איזה גופי").queue();
                return;
            }
            //noinspection DataFlowIssue
            if (!self.hasPermission(memberVoiceState.getChannel())) {
                channel.sendMessage("אין לי גישה").queue();
                return;
            }
            AudioChannelUnion memberChannel = memberVoiceState.getChannel();
            audioManager.openAudioConnection(memberChannel);
            channel.sendMessage("הנני").queue();
        }
        catch (Throwable throwable){
            channel.sendMessage("וואי סליחה רצח אבל לא מצליח להיכנס...").queue();
            throwable.printStackTrace();
        }

    }

    @Override
    public String prefix() {
        return "join";
    }

    @Override
    public String explain() {
        return "מכניס את הבוט לצאנל של שולח הפקודה";
    }
    @Override
    public String getType() {
        return "music";
    }
}
