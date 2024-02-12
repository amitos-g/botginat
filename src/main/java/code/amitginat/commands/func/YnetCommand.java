package code.amitginat.commands.func;

import code.amitginat.commands.AbstractCommand;
import code.amitginat.other.YnetUtil;

public class YnetCommand extends AbstractCommand {
    @Override
    public void run() {
        if (YnetUtil.isActive){
            YnetUtil.stop();
            channel.sendMessage("Stopped!").queue();
        }
        else{
            YnetUtil.channel = channel;
            YnetUtil.activate();
            channel.sendMessage("Activated!").queue();
        }
    }
    @Override
    public String getType() {
        return "func";
    }
    @Override
    public String name() {
        return "ynet";
    }

    @Override
    public String explain() {
        return "שולח עדכונים מווינט כל דקה. כבה בשלחית הפקודה שוב";
    }
}
