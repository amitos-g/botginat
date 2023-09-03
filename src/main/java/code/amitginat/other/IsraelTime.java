package code.amitginat.other;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class IsraelTime {



    public static String get(){

        ZonedDateTime time = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Israel"));
        return String.format("%s:%s:%s", time.getHour(), time.getMinute(), time.getSecond());
    }

}
