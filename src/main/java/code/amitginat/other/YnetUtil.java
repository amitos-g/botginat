package code.amitginat.other;

import java.time.Duration;
import java.time.Instant;


import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.jsoup.Jsoup;

public class YnetUtil{
    public static MessageChannelUnion channel;
    public static Thread runningThread;
    public static boolean isActive;



    public static void activate(){
        isActive = true;
        Thread thread = new Thread(() -> {
            try{
                var before = Instant.now();
                String lastTitle = "";
                while(isActive){
                    var current = Instant.now();
                    if(Duration.between(before, current).toMinutes() == 1){
                        before = current;
                        var doc = Jsoup.connect("https://www.ynet.co.il/news/category/184").get();
                        var accordion = doc.getElementsByClass("Accordion").get(0);
                        var title = accordion.getElementsByClass("titleRow").get(0).getElementsByClass("title").get(0).text();
                        if(!title.equals(lastTitle)){
                            channel.sendMessage(title).queue();
                            lastTitle = title;

                        }
                    }
                }
            }
            catch (Throwable t){
                channel.sendMessage("Error!").queue();
                t.printStackTrace();
            }
        });
        runningThread = thread;
        thread.start();

    }


    public static void stop(){
        isActive = false;
        runningThread.interrupt();
        runningThread = null;
    }
}
