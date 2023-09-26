package code.amitginat.commands.music;

import code.amitginat.commands.AbstractCommand;

public class ClearMusicCommand extends AbstractCommand {




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
        if(musicManager.trackScheduler.queue.isEmpty()){
            channel.sendMessage("אין שירים בתור").queue();
            return;
        }
        musicManager.trackScheduler.queue.clear();
        channel.sendMessage("תור השירים נוקה").queue();
        }
    @Override
    public String prefix() {
        return "clear-music";
    }

    @Override
    public String explain() {
        return "מנקה את השירים שבתור";
    }
    @Override
    public String getType() {
        return "music";
    }
}
