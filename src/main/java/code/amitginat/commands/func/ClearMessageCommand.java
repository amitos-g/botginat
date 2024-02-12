package code.amitginat.commands.func;

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
        try {
            channel.sendMessageFormat("מנקה %s הודעות", amountInt).queue();
            Thread.sleep(200);
            var messageList = channel.getHistory().retrievePast(amountInt + 2).complete();
            channel.asTextChannel().deleteMessages(messageList).queue();

        }
        catch (IllegalArgumentException e){
            channel.sendMessage("לא יכול לנקות הודעות מלפני יותר משבועיים").queue();
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String name() {
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
