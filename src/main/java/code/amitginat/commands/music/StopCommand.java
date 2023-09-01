package code.amitginat.commands.music;

import code.amitginat.commands.AbstractCommand;

public class StopCommand extends AbstractCommand {

    @Override
    public String getType() {
        return "music";
    }


    @Override
    public void run() {
        if (!memberVoiceState.inAudioChannel()) {
            channel.sendMessage("אתה לא נמצא בשיחה").queue();
            return;
        }

        if(!selfVoiceState.inAudioChannel()){
            channel.sendMessage("אני לא נמצא בשיחה").queue();
            return;
        }
        if (musicManager.audioPlayer.getPlayingTrack() == null) {
            channel.sendMessage("אין מה לעצור").queue();
            return;
        }
        if(musicManager.trackScheduler.player.isPaused()){
            channel.sendMessage("כבר עצור").queue();
            return;
        }
        musicManager.trackScheduler.player.setPaused(true);
        channel.sendMessage("עצרתי").queue();
        }
    @Override
    public String prefix() {
        return "stop";
    }

    @Override
    public String explain() {
        return "עוצר את השיר הנוכחי";
    }
}
