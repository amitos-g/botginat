package code.amitginat.commands.music;

import code.amitginat.commands.AbstractCommand;

public class WhatsNextCommand extends AbstractCommand {


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
        var queue = musicManager.trackScheduler.queue;
        if (queue.isEmpty()) {
            channel.sendMessage("אין שום דבר בתור").queue();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (var track : queue) {
            i++;
            String formatted = String.format("%n%s. %s by %s", i, track.getInfo().title, track.getInfo().author);
            stringBuilder.append(formatted);
        }
        channel.sendMessage(stringBuilder).queue();
    }

    @Override
    public String getType() {
        return "music";
    }

    @Override
    public String prefix() {
        return "whats-next";
    }

    @Override
    public String explain() {
        return "מציג את השירים שבתור";
    }
}
