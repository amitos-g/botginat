package code.amitginat.commands.func;
import code.amitginat.commands.AbstractCommand;
import code.amitginat.other.IsraelTime;

public class TimeCommand extends AbstractCommand {
    @Override
    public void run() {
        channel.sendMessage(IsraelTime.get()).queue();
    }
    @Override
    public String getType() {
        return "func";
    }
    @Override
    public String name() {
        return "time";
    }

    @Override
    public String explain() {
        return "מציג את השעה";
    }
}
