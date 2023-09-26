package code.amitginat.commands.music;

import code.amitginat.commands.AbstractCommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

public class NowPlayingCommand extends AbstractCommand {


    @Override
    public void run() {

        if (!selfVoiceState.inAudioChannel()) {
            channel.sendMessage("אני לא בשיחה כבר").queue();
            return;
        }
        if (!memberVoiceState.inAudioChannel()) {
            channel.sendMessage("אתה לא בשיחה איזה גופי").queue();
            return;
        }
        if (musicManager.audioPlayer.getPlayingTrack() == null) {
            channel.sendMessage("שום דבר לא מנגן").queue();
            return;
        }
        AudioTrackInfo info = musicManager.audioPlayer.getPlayingTrack().getInfo();
        channel.sendMessageFormat("Playing %s by %s", info.title, info.author).queue();
    }

    @Override
    public String prefix() {
        return "now-playing";
    }

    @Override
    public String explain() {
        return "מציג את השיר שכרגע מנגן";
    }
    @Override
    public String getType() {
        return "music";
    }
}


