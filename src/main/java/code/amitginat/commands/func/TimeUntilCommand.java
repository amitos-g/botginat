package code.amitginat.commands.func;

import code.amitginat.commands.AbstractCommand;
import code.amitginat.other.IsraelTime;
import org.apache.commons.collections4.BagUtils;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;

public class TimeUntilCommand extends AbstractCommand {
    @Override
    public void run() {
        //ginat time-until 22:20
        String enteredTimeString = message.replace("ginat time-until", "").replace(":","");
        if(!isNumber(enteredTimeString)){
            channel.sendMessage("Not Valid! Send Your Message Like `ginat time-until HH:MM (HOURS:MINUTES)`").queue();
            return;
        }
        String[] timeUnits =  message.replace("ginat time-until", "").strip().split(":");
        if(Integer.parseInt(timeUnits[0]) < 0 || Integer.parseInt(timeUnits[0]) > 24 || Integer.parseInt(timeUnits[1]) < 0 || Integer.parseInt(timeUnits[1]) > 59){
            channel.sendMessage("Not Valid! Send Your Message Like `ginat time-until HH:MM (HOURS:MINUTES)`").queue();
            return;
        }
        LocalTime enteredTimeObject = LocalTime.of(Integer.parseInt(timeUnits[0]),Integer.parseInt(timeUnits[1]), 0);
        LocalTime currentTime = IsraelTime.getLocal();
        long inHours = currentTime.until(enteredTimeObject, ChronoUnit.HOURS);
        long inMinutes = currentTime.until(enteredTimeObject, ChronoUnit.MINUTES) - (inHours * 60);
        long inSeconds = currentTime.until(enteredTimeObject, ChronoUnit.SECONDS) - (inMinutes * 60);
        LocalDate thisDate = LocalDate.now();
        if(currentTime.isAfter(enteredTimeObject)){
            LocalDateTime thisDateTime = LocalDateTime.now();
            var enteredDateTime = LocalDateTime.of(thisDateTime.plusDays(1).toLocalDate(), enteredTimeObject);
            inHours = thisDateTime.until(enteredDateTime, ChronoUnit.HOURS);
            inMinutes = thisDateTime.until(enteredDateTime, ChronoUnit.MINUTES) - (inHours * 60);
            inSeconds = thisDateTime.until(enteredDateTime, ChronoUnit.SECONDS) - (inMinutes * 60);
        }
        channel.sendMessageFormat("%s hours, %s minutes and %s seconds left until %s:%s",
                inHours,
                inMinutes,
                inSeconds,
                timeUnits[0],
                timeUnits[1]).queue();

    }
    @Override
    public String getType() {
        return "func";
    }
    @Override
    public String prefix() {
        return "time-until";
    }

    @Override
    public String explain() {
        return "מציג את הזמן עד השעה שצויינה";
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
