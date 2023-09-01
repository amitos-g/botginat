package code.amitginat.commands.music;

import code.amitginat.commands.AbstractCommand;

public class ResumeCommand extends AbstractCommand {




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
            channel.sendMessage("אין מה להמשיך").queue();
            return;
        }
        if(!musicManager.trackScheduler.player.isPaused()){
            channel.sendMessage("לא עצור").queue();
            return;
        }
        musicManager.trackScheduler.player.setPaused(false);
        channel.sendMessage("המשכתי").queue();
        }
    @Override
    public String getType() {
        return "music";
    }
    @Override
    public String prefix() {
        return "resume";
    }

    @Override
    public String explain() {
        return "ממשיך את השיר הנוכחי";
    }
}
