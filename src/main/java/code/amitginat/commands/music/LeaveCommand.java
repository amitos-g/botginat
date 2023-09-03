package code.amitginat.commands.music;

import code.amitginat.commands.AbstractCommand;
import code.amitginat.other.IsraelTime;

public class LeaveCommand extends AbstractCommand {




    @Override
    public void run() {
        try {
            if (!selfVoiceState.inAudioChannel()) {
                channel.sendMessage("אני לא בשיחה").queue();
                return;
            }
            audioManager.closeAudioConnection();
            channel.sendMessage("ביי").queue();
        }

        catch (Throwable throwable){
            channel.sendMessage("לא מצליח לצאת! נוח לי מדי פה").queue();
            System.out.println(IsraelTime.get() + " ");throwable.printStackTrace();
        }

    }

    @Override
    public String prefix() {
        return "leave";
    }

    @Override
    public String explain() {
        return "מוציא את הבוט מהשיחה שנמצא בה";
    }
    @Override
    public String getType() {
        return "music";
    }
}
