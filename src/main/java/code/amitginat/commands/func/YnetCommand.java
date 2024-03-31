package code.amitginat.commands.func;

import code.amitginat.commands.AbstractCommand;
import code.amitginat.other.YnetUtil;

public class YnetCommand extends AbstractCommand {
    @Override
    public void run() {
        YnetUtil.execute(channel);
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
        return "שולח עדכונים מווינט כל דקה. כבה בשליחת הפקודה שוב";
    }
}
