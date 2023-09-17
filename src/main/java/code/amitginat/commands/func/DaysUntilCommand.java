package code.amitginat.commands.func;

import code.amitginat.commands.AbstractCommand;
import code.amitginat.other.IsraelTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaysUntilCommand extends AbstractCommand {
    @Override
    public void run() {
        //ginat days-until 15:06:2025
        String stringTime = message.replace("ginat days-until", "");
        String enteredTimeString = message.replace("ginat days-until", "").replace(":","");
        if(!isNumber(enteredTimeString)){
            channel.sendMessage("Not Valid! Send Your Message Like `ginat days-until 15:06:2024`").queue();
            return;
        }
        ArrayList<String> dateUnits = new ArrayList<>(List.of(message.replace("ginat days-until", "").strip().split(":")));
        ArrayList<Integer> dateUnitsInt = new ArrayList<>();
        dateUnits.forEach(s -> {
            dateUnitsInt.add(dateUnits.indexOf(s), Integer.parseInt(s));
        });
        //dateUnitsInt[0] == days, //dateUnitsInt[1] == months, //dateUnitsInt[2] == year
        //VALIDATE
        if(dateUnitsInt.get(0) > 31 || dateUnitsInt.get(0) < 1 || dateUnitsInt.get(1) < 1 || dateUnitsInt.get(1)  > 12){
            channel.sendMessage("Not Valid! Send Your Message Like `ginat days-until 15:06:2024`").queue();
            return;
        }
        //Create Date Instances
        var thisDate = LocalDate.now();
        var enteredDate = LocalDate.of(dateUnitsInt.get(2), dateUnitsInt.get(1), dateUnitsInt.get(0));
        if (enteredDate.isBefore(thisDate)) {
            channel.sendMessage(stringTime + " is before today.").queue();
            return;
        }
        var years = thisDate.until(enteredDate, ChronoUnit.YEARS);
        var months = thisDate.until(enteredDate, ChronoUnit.MONTHS) - (years * 12);
        var days = thisDate.until(enteredDate, ChronoUnit.DAYS) - (months > 0 ? (months * 30) : (years * 12 * 30));

        channel.sendMessageFormat("%s years, %s months, and %s days left until %s",
                years,
                months,
                days,
                stringTime

        ).queue();

    }
    @Override
    public String getType() {
        return "func";
    }
    @Override
    public String prefix() {
        return "days-until";
    }

    @Override
    public String explain() {
        return "מציג את הזמן עד התאריך שצויין";
    }

    private boolean isNumber(String string) {
        try{
            Integer.parseInt(string.strip());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
