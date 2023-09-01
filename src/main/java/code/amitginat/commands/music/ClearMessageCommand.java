package code.amitginat.commands.music;

import code.amitginat.commands.AbstractCommand;

public class ClearMessageCommand extends AbstractCommand {




    @Override
    public void run() {
        final String amount = message.replace("ginat clearmsg", "").strip();
        if (!isNumber(amount)) {
            channel.sendMessage("זה לא מספר").queue();
            return;
        }
        int amountInt = Integer.parseInt(amount);
        if (amountInt < 1 || amountInt > 100) {
            channel.sendMessage("יכול לנקות רק בין אחד למאה הודעות").queue();
            return;
        }
        var messageList = channel.getHistory().retrievePast(amountInt + 1).complete();
        channel.asTextChannel().deleteMessages(messageList).queue();
        channel.sendMessageFormat("Cleared %s Messages", amountInt).queue();

    }
    @Override
    public String prefix() {
        return "clearmsg";
    }

    @Override
    public String explain() {
        return "מנקה את כמות השירים שבוקש";
    }
    @Override
    public String getType() {
        return "func";
    }
    private boolean isNumber(String toCheck) {
        try{
            Integer.parseInt(toCheck);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}
